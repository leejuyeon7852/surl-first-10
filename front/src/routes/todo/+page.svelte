<script lang="ts">
	type Todo = {
		id: number;
		body: string;
		done: boolean;
	};

	const todos = $state<Todo[]>([]);

	function addTodo(this: HTMLFormElement) {
		const form: HTMLFormElement = this;

		form.body.value = form.body.value.trim();

		if (form.body.value.length === 0) {
			alert('할일을 입력해주세요.');
			form.body.focus();

			return;
		}

		let body = $state(form.body.value);
		let done = $state(false);

		const todo = {
			id: todos.length + 1,
			get body() {
				return body;
			},
			set body(value: string) {
				body = value;
			},
			get done() {
				return done;
			},
			set done(value: boolean) {
				done = value;
			}
		};

		todos.push(todo);

		form.body.value = '';
		form.body.focus();
	}

	function deleteTodo(todo: Todo) {
		todos.splice(todos.indexOf(todo), 1);
	}

	$effect(() => {
		console.log('todos: 시작');
		for (const todo of todos) {
			console.log(todo);
		}
		console.log('todos: 끝');
	});

	$effect(() => {
		console.log('todos[0]: 시작');
		if (todos.length > 0) {
			console.log('todos[0].id', todos[0].id);
			console.log('todos[0].body', todos[0].body);
			console.log('todos[0].done', todos[0].done);
		}
		console.log('todos[0]: 끝');
	});
</script>

<h1>할일 앱</h1>

<h2>할일 추가</h2>
<!-- svelte-ignore event_directive_deprecated -->
<form on:submit|preventDefault={addTodo}>
	<input type="text" name="body" placeholder="할일을 입력해주세요." autocomplete="off" />
	<button type="submit">추가</button>
</form>

<h2>할일 리스트</h2>

<ul>
	{#each todos as todo (todo.id)}
		<li>
			<input type="checkbox" bind:checked={todo.done} />
			{todo.body}
			<!-- svelte-ignore event_directive_deprecated -->
			<button type="button" on:click|preventDefault={() => deleteTodo(todo)}>삭제</button>
		</li>
	{/each}
</ul>
