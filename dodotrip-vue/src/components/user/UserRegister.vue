<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { signup } from "@/api/user";

const router = useRouter();

const user = ref({
  userEmail: "",
  name: "",
  userPass: "",
  userPassCheck: "" //비밀번호 확인
});

const passwordErrMsg = ref("");

//비밀번호 확인란
watch(
  [() => user.value.userPass, () => user.value.userPassCheck],
  ([password, passwordCheck]) => {
    if (password !== passwordCheck) {
      passwordErrMsg.value = "비밀번호를 동일하게 입력해주세요!";
    } else {
      passwordErrMsg.value = "";
    }
  }
);
function resetForm() {
  user.value = {
    userEmail: "",
    name: "",
    userPass: "",
    userPassCheck: ""
  };
  passwordErrMsg.value = "";
}

function registerMember() {
  console.log("회원 등록하자!!", user.value);
  signup(
    user.value,
    (response) => {
      let msg = "회원등록 중 문제 발생했습니다.";
      if (response.status == 201) msg = "회원가입이 완료되었습니다.";
      alert(msg);
      router.push({ name: "main" });
    },
    (error) => console.error(error)
  );
}

function onSubmit() {
  if (passwordErrMsg.value) {
    alert(passwordErrMsg.value);
  } else {
    registerMember();
  }
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div id="container">
      <div class="col-lg-8">
        <h2 class="my-3 py-3 text-center">
          회원가입
        </h2>
      </div>
      <div class="col">
        <form>
          <div class="sub-1">
            <div class="sub-1-1">
              <div class="mb-3">
                <input type="text" class="form-control" placeholder="이메일..." v-model="user.userEmail" />
              </div>
              <div class="mb-3">
                <input type="text" class="form-control" placeholder="이름..." v-model="user.name" />
              </div>
              <div class="mb-3">
                <input type="password" class="form-control" placeholder="비밀번호..." v-model="user.userPass" />
              </div>
              <div class="mb-3">
                <input type="password" class="form-control" id="pwdcheck" placeholder="비밀번호 확인..."
                  v-model="user.userPassCheck" />
              </div>
              <div class="sub-1-2">
                <div class="col-auto text-center">
                  <button type="button" class="btn btn-outline-secondary mb-3" @click="registerMember">회원가입</button>
                  <button type="button" class="btn btn-outline-secondary mb-3" @click="resetForm">초기화</button>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </form>
</template>

<style scoped>
#container {
  width: 100% !important;
  height: 600px;
  background-color: #F2F2F2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 6% 0 0 0 !important;
  border: 1px solid black;
}


.sub-1 {
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: center;
}

.sub-1-1 {
  width: 360px;
  height: auto; /* Set height to auto for dynamic height based on content */
}

.sub-1-1 input {
  border: 1px solid black;
  margin-top: 8px; /* Add margin to the top of the input fields */
  margin-bottom: 8px; /* Add margin to the bottom of the input fields */
}

.sub-1-1 button {
  background-color: black;
}

.sub-1-1 button span {
  color: #F2F2F2;
}

.sub-1-2 {
  width: 360px;
  height: auto; /* Set height to auto for dynamic height based on content */
  display: flex;
  justify-content: center;
}

.sub-1-2 div button {
  width: 163px;
  height: 36px;
  background-color: rgb(221, 216, 216);
  margin-right: 10px; /* Add margin to create space between buttons */

}
</style>
