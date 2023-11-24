import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "@/views/TheMainView.vue";

//자식 url에는 "/" 슬래쉬 쓰면 X
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/plan",
      name: "plan",
      component: () => import("@/components/plan/PlanWrite.vue"),
    },
    {
      path: "/plan/view/:planno",
      name: "plan-view",
      component: () => import("@/components/plan/PlanDetail.vue"),
    },
    {
      path: "/plan/write/:planno",
      name: "plan-write",
      component: () => import("@/components/plan/item/PlanFormItem.vue"),
    },
    {
      path: "/attraction",
      name: "attraction",
      component: () => import("../views/TheAttractionView.vue"),
      redirect: { name: "attraction-search" },
      children: [
        {
          path: "search",
          name: "attraction-search",
          component: () =>
            import("@/components/attraction/AttractionSearch.vue"),
        },
        {
          path: 'detail',
          name: 'attraction-detail',
          component: () =>
            import('@/components/attraction/AttractionLikeDetail.vue'),
        },
      ],
    },
    {
      path: "/attraction/like",
      name: "attraction-like",
      component: () => import("../views/TheAttractionLike.vue"),
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/qna",
      name: "qna",
      component: () => import("../views/TheQnAView.vue"),
      redirect: { name: "qna-list" },
      children: [
        {
          path: "list",
          name: "qna-list",
          component: () => import("@/components/qna/QnAList.vue"),
        },
        {
          path: "view/:qnano",
          name: "qna-view",
          component: () => import("@/components/qna/QnADetail.vue"),
        },
        {
          path: "write",
          name: "qna-write",
          component: () => import("@/components/qna/QnAWrite.vue"),
        },
        {
          path: "writeAdmin/:qnano",
          name: "qna-adminWrite",
          component: () => import("@/components/qna/item/QnAFormAdmin.vue"),
        },
      ],
    },
    {
      path: "/login",
      name: "user-login",
      component: () => import("@/components/user/UserLogin.vue"),
    },
    {
      path: "/signup",
      name: "user-signup",
      component: () => import("@/components/user/UserRegister.vue"),
    },
    {
      path: "/mypage",
      name: "user-mypage",
      component: () => import("@/components/user/UserMyPage.vue"),
    },
    {
      path: "/modify",
      name: "user-modify",
      component: () => import("@/components/user/UserModify.vue"),
    },
  ],
});



export default router;
