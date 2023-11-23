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
  const userInfo =  await getUserInfo(token);
  console.log("userinfo : " , userInfo);
  userEmail.value = userInfo.userEmail;
  console.log("userEmail : ", userEmail.value);
});
</script>

<template>
  <div class="comment-container">
    <div v-if="!props.comment">
      <p style="height: 400px">댓글이 없습니다.</p>
    </div>
    <div v-else>
      <div class="comment-item">
        <div class="comment-header">
          <span class="comment-author">{{ props.comment.memberUserEmail }}</span>
          <span class="comment-date">{{ props.comment.createTime }}</span>
          <!-- 댓글 작성자와 현재 사용자의 이메일이 일치하는 경우에만 삭제 버튼을 표시 -->
          <button v-if="userEmail === props.comment.memberUserEmail" class="delete-button" @click="onDeleteComment">삭제</button>
          <!-- <button class="delete-button" @click="onDeleteComment">삭제</button> -->
        </div>
        <div class="comment-content">{{ props.comment.content }}</div>
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
  height: 100px; /* 댓글 목록의 높이 */
}

.comment-item {
  width: 1000px;
  border: 1px solid #ddd;
  margin-bottom: 5px; /* 댓글 목록 간격 조절 */
  padding: 10px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
}

.comment-author {
  font-weight: bold;
}

.comment-date {
  color: #888;
}

.comment-content {
  margin-top: 5px;
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
}
</style>
