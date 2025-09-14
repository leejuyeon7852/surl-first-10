<script lang="ts">
	class GlobalError extends Error {
		rs: any;
		constructor(rs: any) {
			super(rs?.msg ?? '알 수 없는 에러');
			this.rs = rs;
		}
	}

	type Member = {
		id: number;
		createDate: string;
		modifyDate: string;
		username: string;
		nickname: string;
	};

	let member: Member | null = $state(null); //뷰에서 사용될 때 초기값 state로 설정
	let errorMessage: string | null = $state(null);

	async function getMe() {
		try {
			const response = await fetch(`${import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/members/me`, {
				credentials: 'include'
			});

			const rs = await response.json();

			if (!response.ok) {
				throw new GlobalError(rs);
			}

			member = rs.data.item;
		} catch (error: any) {
			errorMessage = error.rs.msg;
			console.error(error.rs);
		}
	}

	$effect(() => {
		getMe();
	});
</script>

{#if member}<!--이렇게 사용될 때-->
	<div>
		<div>
			<span>아이디</span>
			<span>{member.username}</span>
		</div>
		<div>
			<span>닉네임</span>
			<span>{member.nickname}</span>
		</div>
		<div>
			<span>가입일</span>
			<span>{member.createDate}</span>
		</div>
		<div>
			<span>수정일</span>
			<span>{member.modifyDate}</span>
		</div>
	</div>
{:else if errorMessage !== null}
	<div>에러 발생: {errorMessage}</div>
{/if}
