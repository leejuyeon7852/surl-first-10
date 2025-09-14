<script lang="ts">
	const todos = $state<any[]>([]);

	function addTodo(this: HTMLFormElement) {
		const form: HTMLFormElement = this;

		form.body.value = form.body.value.trim();

		if (form.body.value.length === 0) {
			alert('할일을 입력해주세요.');
			form.body.focus();

			return;
		}

		todos.push({
			id: todos.length + 1,
			body: form.body.value,
			done: true
		});

		form.body.value = '';
		form.body.focus();
	}

	$effect(() => {
		console.log('todos: 시작');
		for (const todo of todos) {
			console.log(todo);
		}
		console.log('todos: 끝');
	});
</script>

<h1>할일 앱</h1>

<h2>할일 추가</h2>
<!-- svelte-ignore event_directive_deprecated -->
<form on:submit|preventDefault={addTodo}>
	<input type="text" name="body" placeholder="할일을 입력해주세요." />
	<button type="submit">추가</button>
</form>

<h2>할일 리스트</h2>

<ul>
	{#each todos as todo (todo.id)}
		<li>
			<input type="checkbox" bind:checked={todo.done} />
			{todo.body}
		</li>
	{/each}
</ul>
