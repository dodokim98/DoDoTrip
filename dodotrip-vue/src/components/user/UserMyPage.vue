<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { useRoute, useRouter } from "vue-router";
import { deleteMember } from "@/api/user";

const router = useRouter();
const memberStore = useMemberStore();

const { getUserInfo } = memberStore;

const user = ref({});

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token); //유저의 role 권한 얻기 위해 메소드 호출
  user.value = userInfo;
});

function moveModfiy() {
  console.log("move modify button");
  router.push({ name: "user-modify" });
}

function onDeleteMember() {
  deleteMember(user.value.userEmail, ({ data }) => {
    console.log(data);
    alert("탈퇴되었습니다.");
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("refreshToken");
    if(cookies.get("id")){
      cookies.remove("id");
    }
    router.replace({ name: "user-login" });
  });
}
</script>

<template>
  <div id="container">
    <button
      type="button"
      class="btn btn-dark"
      @click="moveModfiy"
      style="
        width: 219px;
        height: 56px;
        left: 1000px;
        top: 630px;
        position: absolute;
        background: black;
        z-index: 1000;
      "
    >
      나의 정보 수정
    </button>
    <button
      type="button"
      class="btn btn-dark"
      @click="onDeleteMember"
      style="
        width: 219px;
        height: 56px;
        left: 1250px;
        top: 630px;
        position: absolute;
        background: black;
        z-index: 1000;
      "
    >
      회원 탈퇴
    </button>

    <div
      class="Fieldset"
      style="
        width: 728px;
        height: 570px;
        left: 888px;
        top: 160px;
        position: absolute;
      "
    >
      <div
        class="Rectangle3"
        style="
          width: 728px;
          height: 561.44px;
          left: 0px;
          top: 18.56px;
          position: absolute;
          background: rgba(217, 217, 217, 0);
          border: 1px black solid;
        "
      ></div>
      <div
        class="Group1"
        style="
          width: 130.88px;
          height: 37.12px;
          left: 21px;
          top: 0px;
          position: absolute;
        "
      >
        <div
          class="Rectangle4"
          style="
            width: 111px;
            height: 10px;
            left: 0px;
            top: 15px;
            position: absolute;
            background: #f2f2f2;
          "
        ></div>
        <div
          class="Info"
          style="
            width: 111.71px;
            height: 37.12px;
            left: 19.17px;
            top: 0px;
            position: absolute;
            color: black;
            font-size: 32px;
            font-family: Poppins;
            font-weight: 700;
            word-wrap: break-word;
          "
        >
          Info
        </div>
      </div>
    </div>
    <div
      class="Component2"
      style="
        width: 360px;
        height: 372px;
        left: 650px;
        top: 200px;
        position: absolute;
      "
    >
      <div
        class="Input"
        style="
          width: 360px;
          height: 64px;
          left: 400px;
          top: 308px;
          position: absolute;
        "
      >
        <div
          class="Input"
          style="
            width: 360px;
            height: 64px;
            left: 0px;
            top: 0px;
            position: absolute;
            background: white;
            border-radius: 5px;
            border: 1px black solid;
          "
        ></div>
        <div
          class="Placeholder"
          style="
            left: 25px;
            top: 20px;
            position: absolute;
            color: rgba(0, 0, 0, 0.5);
            font-size: 20px;
            font-family: Roboto;
            font-weight: 400;
            line-height: 28.12px;
            word-wrap: break-word;
          "
        >
          {{ user.joinDate }}
        </div>
      </div>
      <div
        class="Input"
        style="
          width: 360px;
          height: 64px;
          left: 400px;
          top: 96px;
          position: absolute;
        "
      >
        <div
          class="Input"
          style="
            width: 360px;
            height: 64px;
            left: 0px;
            top: 0px;
            position: absolute;
            background: white;
            border-radius: 5px;
            border: 1px black solid;
          "
          placeholder="아이디..."
        ></div>
        <div
          class="Placeholder"
          style="
            left: 25px;
            top: 20px;
            position: absolute;
            color: rgba(0, 0, 0, 0.5);
            font-size: 20px;
            font-family: Roboto;
            font-weight: 400;
            line-height: 28.12px;
            word-wrap: break-word;
          "
        >
          {{ user.name }}
        </div>
      </div>

      <div
        class="Input"
        style="
          width: 360px;
          height: 64px;
          left: 400px;
          top: 202px;
          position: absolute;
        "
      >
        <div
          class="Input"
          style="
            width: 360px;
            height: 64px;
            left: 0px;
            top: 0px;
            position: absolute;
            background: white;
            border-radius: 5px;
            border: 1px black solid;
          "
        ></div>
        <div
          class="Placeholder"
          style="
            left: 25px;
            top: 20px;
            position: absolute;
            color: rgba(0, 0, 0, 0.5);
            font-size: 20px;
            font-family: Roboto;
            font-weight: 400;
            line-height: 28.12px;
            word-wrap: break-word;
          "
        >
          {{ user.userEmail }}
        </div>
      </div>
    </div>
    <div
      style="
        width: 93px;
        height: 36px;
        left: 950px;
        top: 425px;
        position: absolute;
        color: black;
        font-size: 20px;
        font-family: Roboto;
        font-weight: 400;
        line-height: 28.12px;
        word-wrap: break-word;
      "
    >
      이메일
    </div>
    <div
      style="
        width: 60px;
        height: 32px;
        left: 950px;
        top: 320px;
        position: absolute;
        color: black;
        font-size: 20px;
        font-family: Roboto;
        font-weight: 400;
        line-height: 28.12px;
        word-wrap: break-word;
      "
    >
      이름
    </div>
    <div
      style="
        width: 93px;
        height: 36px;
        left: 950px;
        top: 530px;
        position: absolute;
        color: black;
        font-size: 20px;
        font-family: Roboto;
        font-weight: 400;
        line-height: 28.12px;
        word-wrap: break-word;
      "
    >
      가입일
    </div>
    <img
      class="Rectangle5"
      style="
        width: 318px;
        height: 243px;
        left: 430px;
        top: 285px;
        position: absolute;
        background: linear-gradient(0deg, #d9d9d9 0%, #d9d9d9 100%);
      "
      src="@/assets/dog.png"
    />
    <div
      class="Frame7"
      style="
        width: 156px;
        height: 98px;
        left: 656px;
        top: 919px;
        position: absolute;
      "
    ></div>
  </div>
</template>

<style scoped>
#container {
  width: 100% !important;
  height: 650px;
  background-color: #f2f2f2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 3% 0 0 0 !important;
  border: 1px solid black;
}
</style>
