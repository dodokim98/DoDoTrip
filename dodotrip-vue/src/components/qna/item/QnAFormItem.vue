<script setup>
import { ref, watch, onMounted } from "vue";
import { useRouter } from "vue-router";
import { registQna } from "@/api/qna";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const memberStore = useMemberStore();

const { getUserInfo } = memberStore;
const userRole = ref("");

//관리자가 접속한다면 답변 달기 버튼 기능 활성화, 일반 유저 접속 시 질문 입력폼만 있음
//QnA는 수정기능 없음

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token);  //유저의 role 권한 얻기 위해 메소드 호출
  console.log(userInfo.role)
  userRole.value = userInfo.role;
  // userRole.value = 'ADMIN';
});


const qna = ref({
  qnaNo: 0,
  question: "",
  answer: "",
  memberUserEmail: "",
});

const subjectErrMsg = ref("");
const contentErrMsg = ref("");

watch(
  () => qna.value.question,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "질문을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  }
);
watch(
  () => qna.value.answer,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "답변을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  }
);

function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    writeQna();
  }
}

function writeQna() {
  console.log("글을 등록합니다!", qna.value);
  registQna(
    qna.value,
    (response) => {
      let msg = "글 등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글 등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function moveList() {
  router.push({ name: "qna-list" });
}
</script>
<template>
  <div class="container">
    <form @submit.prevent="onSubmit">
      <template v-if="userRole === 'USER'">
        <!-- USER 폼 -->
        <div class="mb-3">
          <label for="question" class="form-label">Q. </label>
          <input type="text" class="form-control" v-model="qna.question" placeholder="질문" />
        </div>
      </template>

      <div class="col-auto text-center">
        <button type="submit" class="btn btn-primary mb-3">
          글작성
        </button>
        <button type="button" class="btn btn-danger mb-3 ms-1" @click="moveList">
          목록으로 이동...
        </button>
      </div>
    </form>
  </div>
</template>


<style scoped>
/* 버튼 스타일 */
.btn {
  font-size: 16px;
  padding: 10px 20px;
}

</style>