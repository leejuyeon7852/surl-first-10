<script lang="ts">
	async function submitLoginForm(this: HTMLFormElement) {
		const form: HTMLFormElement = this;

		form.username.value = form.username.value.trim();

		if (form.username.value.length === 0) {
			alert('아이디를 입력해주세요.');
			form.username.focus();
			return;
		}

		form.password.value = form.password.value.trim();

		if (form.password.value.length === 0) {
			alert('비밀번호를 입력해주세요.');
			form.password.focus();
			return;
		}

		const rs = await fetch(`${import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/members/login`, {
			method: 'POST',
			credentials: 'include',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				username: form.username.value,
				password: form.password.value
			})
		}).then((res) => res.json());
		console.log(rs);
	}
</script>

<form on:submit|preventDefault={submitLoginForm}>
	<div>
		<label>
			<span>username</span>
			<input type="text" name="username" placeholder="아이디" />
		</label>
	</div>
	<div>
		<label>
			<span>비밀번호</span>
			<input type="password" name="password" placeholder="비밀번호" />
		</label>
	</div>
	<div>
		<label>
			<span>회원가입</span>
			<input type="submit" value="회원가입" />
		</label>
	</div>
</form>
