<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();
const router = useRouter();
const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userEmail: "",
  userPass: "",
});

const saveEmailCheckbox = ref(false);


// 컴포넌트가 마운트되었을 때 쿠키에 저장된 이메일을 확인하고 체크박스도 갱신
onMounted(() => {
  if (cookies.get("saveid")) {
    loginUser.value.userEmail = cookies.get("saveid");
    saveEmailCheckbox.value = true; // 체크박스도 체크해야 함
  }
});

const login = async () => {
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");

  if (isLogin || cookies.get('id') !== null) {
    console.log("로그인 성공");
    getUserInfo(token);
    changeMenuState(); //메뉴바 상태 바꾸기

    if (saveEmailCheckbox.value) {
      cookies.set("saveid", loginUser.value.userEmail);
    } else {
      cookies.remove("saveid");
    }
  }
  router.push("/");
};
</script>


<template>
  <div id="container">
    <div class="col-lg-8">
      <h2 class="my-3 py-3 text-center">
        로그인
      </h2>
    </div>
    <div class="col">
      <form>
        <div class="sub-1">
          <div class="sub-1-1">
            <div class="form-check mb-3 float-end">
              <input class="form-check-input" type="checkbox" id="saveid" v-model="saveEmailCheckbox" />
              <label class="form-check-label" for="saveid"> 아이디저장 </label>
            </div>
            <div class="mb-3 text-start">
              <input type="text" class="form-control" id="userEmail" v-model="loginUser.userEmail" placeholder="이메일..." />
            </div>
            <div class="mb-3 text-start">
              <input type="password" class="form-control" id="userPass" v-model="loginUser.userPass" @keyup.enter="login"
                placeholder="비밀번호..." />
            </div>
            <div class="mb-3 text-start">
              <button type="button" class="form-control" @click="login()">
                <span class="loginBtn">로그인</span>
              </button>
            </div>
            <div class="sub-1-2">
              <div class="mb-3 text-start">
                <button type="button" class="btn btn-outline-secondary mb-3">
                  <router-link :to="{ name: 'user-signup' }" class="nav-link signupBtn">회원가입</router-link>
                </button>
              </div>
              <div class="mb-3 text-start">
                <button type="button" class="btn btn-outline-secondary mb-3">
                  <span class="actionBtn">비밀번호 찾기</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
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

.col-lg-10 {
  margin-bottom: 100px;
  /* Adjust the value as needed */
}

.sub-1 {
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: center;
}

.sub-1-1 {
  width: 360px;
  height: 64px;
}

.sub-1-1 input {
  border: 1px solid black;
}

.sub-1-1 button {
  background-color: black;
  margin-right: 15px;
}

.sub-1-1 button span {
  color: #F2F2F2;
}

.sub-1-2 {
  width: 360px;
  height: px;
  display: flex;
  justify-content: center;
}

.sub-1-2 div button {
  width: 163px;
  height: 36px;
  background-color: rgb(221, 216, 216);
  margin-left: 15px;
}
</style>
