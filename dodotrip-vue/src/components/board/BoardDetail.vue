<script setup>
import { ref, onMounted } from "vue";
import { detailArticle, deleteArticle } from "@/api/board";
import { useRoute, useRouter } from "vue-router";
import { getCommentList, registComment } from "@/api/comment.js";

import TheComment from "@/views/TheComment.vue";
import { useMemberStore } from "@/stores/member";


const route = useRoute();
const router = useRouter();
const articleno = route.params.articleno;
const article = ref({});
const comments = ref([]);
const newComment = ref('');

const userEmail = ref();
const memberStore = useMemberStore();
const { getUserInfo } = memberStore;

onMounted(async () => {
  console.log("Component mounted!");
  let token = sessionStorage.getItem("accessToken");
  const userInfo = await getUserInfo(token);
  console.log("userinfo : ", userInfo);
  userEmail.value = userInfo.userEmail;
  console.log("userEmail : ", userEmail.value);
  getArticle();
  loadComments();
});

const getArticle = () => {
  console.log("22222222222");
  detailArticle(articleno, ({ data }) => {
    console.log("data : ", data);
    article.value = data;
    console.log("articles : ", article.value);

    // getCommentList를 여기서 호출
    getCommentList(
      articleno,
      ({ data }) => {
        console.log("articleno :", articleno);
        console.log("data : ", data);
        comments.value = data;
        console.log("comments : ", comments.value);
      },
      (error) => {
        console.error(error);
      }
    );
  });
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(articleno, ({ data }) => {
    console.log(data);
    router.replace({ name: "article-list" });
  });
}

const loadComments = () => {
  getCommentList(
    articleno,
    ({ data }) => {
      comments.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onAddComment = () => {
  console.log('댓글 추가:', newComment.value);

  // commentDto 객체 생성
  const commentDto = {
    boardArticleNo: articleno,
    memberUserEmail: userEmail.value, // 댓글을 작성한 사용자 이메일
    content: newComment.value
  };

  console.log("commentDto : ", commentDto);

  // 댓글 등록 API 호출
  registComment(
    commentDto,
    ({ data }) => {
      console.log('댓글 등록 성공:', data);
      // 등록 성공 후 댓글 목록을 다시 불러옴
      getCommentList(articleno, ({ data }) => {
        comments.value = data;
      });
    },
    (error) => {
      console.error('댓글 등록 실패:', error);
    }
  );

  // 댓글 입력창 초기화
  newComment.value = '';


};

</script>

<template>
  <div class="container mt-4">
    <div class="bg-dark text-center py-3">
      <h2 class="text-light">글보기</h2>
    </div>
    <div class="row justify-content-center mt-4">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <h2 class="card-title float-md-start">{{ article.subject }}</h2>
            <div class="clearfix">
              <p class="card-text float-md-end p-2">
                <img class="avatar " src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
                <span class="fw-bold">{{ article.memberUserEmail }}</span><br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }} 조회: {{ article.hit }}
                </span>
              </p>
              <div class="divider mb-5"></div>
              <div class="card-text float-md-start p-2">{{ article.content }}</div>
            </div>
            <div class="divider mt-3 mb-3"></div>
            <div class="d-flex justify-content-end">
              <button type="button" class="btn btn-outline-primary mb-3 me-1" @click="moveList">글목록</button>
              <button type="button" class="btn btn-outline-success mb-3 me-1" @click="moveModify">글수정</button>
              <button type="button" class="btn btn-outline-danger mb-3" @click="onDeleteArticle">글삭제</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <TheComment v-for="comment in comments" :key="comment.commentNo" :comment="comment">
    </TheComment>
    <!-- 새로운 댓글 등록을 위한 textarea와 버튼 -->
    <!-- <div>
          <textarea v-model="newComment" placeholder="댓글을 입력하세요"></textarea>
          <button @click="onAddComment">댓글 등록</button>
        </div> -->
    <div class="mt-4">
      <div class="input-group mb-2">
        <textarea v-model="newComment" class="form-control" placeholder="댓글을 입력하세요" rows="3"></textarea>
        <button @click="onAddComment" class="btn btn-primary ms-3">
          댓글 등록
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 10px;
  height: 700px;
}

.bg-dark {
  background-color: #343a40 !important;
  /* Updated to a slight black background color */
}

.bg-secondary {
  background-color: #6c757d !important;
  /* Updated to gray background color */
}

.text-light {
  color: #ffffff !important;
  /* Updated to white text color */
}

.text-secondary {
  color: #6c757d !important;
  /* Updated to gray text color */
}

.bg-gray {
  background-color: #f2f2f2 !important;
  /* Updated to gray background color for the main content */
}

.avatar {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 50%;
}

.text {
  line-height: 1.6;
  font-size: 16px;
  color: #495057;
}

.bg-dark {
  background-color: #343a40 !important;
  /* Updated to a slight black background color */
}

.bg-secondary {
  background-color: #6c757d !important;
  /* Updated to gray background color */
}

.text-light {
  color: #ffffff !important;
  /* Updated to white text color */
}

.text-secondary {
  color: #6c757d !important;
  /* Updated to gray text color */
}

.bg-gray {
  background-color: #f2f2f2 !important;
  /* Updated to gray background color for the main content */
}

/* textarea와 버튼 스타일 */
textarea {
  width: 70%;
  margin-bottom: 5px;
  padding: 5px;
  vertical-align: top;
  /* 상단 정렬 추가 */
}

button {
  background-color: #007bff;
  color: #fff;
  padding: 5px 10px;
  border: none;
  cursor: pointer;
  font-size: 14px;
  border-radius: 5px;
  vertical-align: top;
  /* 상단 정렬 추가 */
  margin-left: 5px;
}
</style>
