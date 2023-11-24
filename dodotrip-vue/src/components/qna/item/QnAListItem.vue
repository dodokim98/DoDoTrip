<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;
const userRole = ref("");
defineProps({ qna: Object });

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token); //유저의 role 권한 얻기 위해 메소드 호출
  console.log(userInfo.role);
  userRole.value = userInfo.role;
  // userRole.value = 'ADMIN';
});
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ qna.qnaNo }}</th>
    <td class="text-start">
      <router-link
        :to="{
          name: 'qna-view',
          params: { qnano: qna.qnaNo },
        }"
        class="article-title link-dark"
      >
        {{ qna.question }}
      </router-link>
    </td>
    <td>{{ qna.memberUserEmail }}</td>
    <td v-if="!qna.answer">
      <button v-if="userRole === 'ADMIN'">
        <router-link
          :to="{
            name: 'qna-adminWrite',
            params: { qnano: qna.qnaNo },
          }"
          class="article-title link-dark"
        >
          답변하기
        </router-link>
      </button>
    </td>
    <td v-else>답변완료</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}

.article-title {
  text-align: center;
}
</style>
