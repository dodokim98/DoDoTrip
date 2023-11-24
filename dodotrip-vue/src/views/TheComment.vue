<script setup>
import { defineProps, onMounted, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { deleteComment } from '@/api/comment';
import { useMemberStore } from "@/stores/member";

const props = defineProps({ comment: Object });
const route = useRoute();
const router = useRouter();
const articleno = route.params.articleno;

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;
const userEmail = ref();

const onDeleteComment = () => {
  const { commentNo } = props.comment;
  deleteComment(
    commentNo,
    ({ data }) => {
      console.log('댓글 삭제 성공:', data);
      console.log("articleno : ", articleno);
      alert("댓글 삭제가 완료되었습니다.");
      router.go(0);
    },
    (error) => {
      console.error('댓글 삭제 실패:', error);
    }
  );
};

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken");
  const userInfo = await getUserInfo(token);
  console.log("userinfo : ", userInfo);
  userEmail.value = userInfo.userEmail;
  console.log("userEmail : ", userEmail.value);
});
</script>

<template>
  <div class="comment-container mt-4">
    <div v-if="!props.comment">
      <p class="card p-3">댓글이 없습니다.</p>
    </div>
    <div v-else>
      <div class="card mb-2">
        <div class="card-body">
          <div class="comment-header d-flex justify-content-between ">
            <span class="comment-author">{{ props.comment.memberUserEmail }}</span>
            <span class="comment-date">{{ props.comment.createTime }}</span>
            <button v-if="userEmail === props.comment.memberUserEmail" class="btn btn-sm btn-danger"
              @click="onDeleteComment">삭제</button>
          </div>
          <div class="comment-content text-start">{{ props.comment.content }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.comment-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
}
.card {
  width: 1257px; /* 가로폭을 100%로 설정 */
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 10px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
/* .comment-item {
  width: 100%;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 10px;
  /* 댓글 간격 조절 */
/* padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
} */

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.comment-author {
  font-weight: bold;
  color: #007bff;
}

.comment-date {
  color: #888;
}

.comment-content {
  font-size: 14px;
  color: #495057;
  margin-left: auto;
}

/* 삭제 버튼 스타일 */
.delete-button {
  background-color: #dc3545;
  color: #fff;
  padding: 5px 10px;
  border: none;
  cursor: pointer;
  font-size: 14px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.delete-button:hover {
  background-color: #c82333;
}
</style>