<script setup>
import { useMenuStore } from "@/stores/menu";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const memberStore = useMemberStore();
const menuStore = useMenuStore();
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;
const { userLogin, userLogout, getUserInfo } = memberStore;
const router = useRouter();


const logout = async () => {
  console.log("로그아웃!!!!");
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token); //유저의 role 권한 얻기 위해 메소드 호출
  console.log("userInfo: {}", userInfo);

  userLogout(userInfo.userEmail);
  changeMenuState(); //메뉴바 상태 바꾸기
  router.push("/login");
};

</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div
      class="container-fluid d-flex justify-content-between align-items-center"
    >
      <router-link :to="{ name: 'main' }" class="navbar-brand" id="dodotrip"
        >DODOTRIP</router-link
      >
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul
          class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <li class="nav-item">
            <router-link :to="{ name: 'attraction' }" class="nav-link"
              >지역별 관광지</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'plan' }" class="nav-link"
              >나의 여행계획</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'board' }" class="nav-link"
              >여행정보공유</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'attraction-like' }" class="nav-link"
              >찜한 여행지</router-link
            >
          </li>

          <li class="nav-item">
            <router-link :to="{ name: 'qna' }" class="nav-link"
              >QnA</router-link
            >
          </li>

          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'user-logout'">
                <li
                  class="nav-item"
                  style="
                    border: 2px solid black;
                    border-radius: 10px;
                    width: 100px;
                    text-align: center;
                  "
                >
                  <router-link
                    to="/login"
                    @click.prevent="logout"
                    class="nav-link"
                    >{{ menu.name }}</router-link
                  >
                </li>
              </template>
              <template v-else>
                <template v-if="menu.routeName === 'user-login'">
                  <li
                    class="nav-item"
                    style="
                      border: 2px solid black;
                      border-radius: 10px;
                      width: 100px;
                      text-align: center;
                    "
                  >
                    <router-link
                      :to="{ name: menu.routeName }"
                      class="nav-link"
                      >{{ menu.name }}</router-link
                    >
                  </li>
                </template>
                <template v-else>
                  <li class="nav-item">
                    <router-link
                      :to="{ name: menu.routeName }"
                      class="nav-link"
                      >{{ menu.name }}</router-link
                    >
                  </li>
                </template>
              </template>
            </template>
          </template>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
@font-face {
  font-family: "Noto Sans KR", sans-serif;
  src: url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"); /* Noto Sans Font */
}

* {
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 600;
}
#dodotrip {
  font-weight: bolder;
}

#login {
  border: 2px solid black;
  border-radius: 10px;
  width: 100px;
  text-align: center;
}
</style>
