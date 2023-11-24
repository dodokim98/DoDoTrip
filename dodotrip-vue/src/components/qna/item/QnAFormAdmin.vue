<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailQna, registAnswer } from "@/api/qna";
import { useMemberStore } from "@/stores/member";

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();

// const articleno = ref(route.params.articleno);
const qnaNo = ref(0);
const qna = ref({});
const answer = ref();

const { getUserInfo } = memberStore;
const userRole = ref(""); //로그인한 유저의 권한

onMounted(() => {
  getQna();
});

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token); //유저의 role 권한 얻기 위해 메소드 호출
  console.log(userInfo.role);
  userRole.value = userInfo.role;
  // userRole.value = 'ADMIN';
});

const getQna = () => {
  qnaNo.value = route.params.qnano;
  console.log(qnaNo.value + "번 글 얻으러 가자!!!");

  detailQna(
    qnaNo.value,
    ({ data }) => {
      console.log(data);
      qna.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
const onSubmit = () => {
  saveAnswer(answer);
};

const saveAnswer = (answer) => {
  console.log(answer);
  qna.value.answer = answer;
  console.log("답변을 등록합니다!", qna.value.answer);

  registAnswer(
    qna.value,
    (response) => {
      let msg = "답변 등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "답변 등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
};

function moveList() {
  router.push({ name: "qna-list" });
}
</script>

<template>
  <header>
    <h1>QnA 게시판</h1>
  </header>

  <main>
    <div class="qna-item">
      <div class="question">
        질문 <span id="question1">{{ qna.question }}</span>
      </div>
      <div class="user">
        작성자: <span id="user1">{{ qna.memberUserEmail }}</span>
      </div>

      <div class="answer">
        <form @submit.prevent="onSubmit">
          <div class="mb-3">
            <label for="adminInput" class="form-label">답변</label>
            <input type="text" class="form-control" v-model="answer" />
          </div>
          <button type="submit" class="btn btn-primary">답변 저장</button>
        </form>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  max-width: 800px;
  margin: 20px auto;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.qna-item {
  cursor: pointer;
  margin-bottom: 20px;
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
}

.question {
  font-weight: bold;
  margin-bottom: 8px;
}

.answer {
  margin-left: 20px;
}

.user {
  color: #888;
}
</style>
