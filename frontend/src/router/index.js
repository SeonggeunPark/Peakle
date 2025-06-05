import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MapView from '@/views/MapView.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import CalenderView from '@/components/user/CalenderView.vue'
import UserSignUp from '@/components/user/UserSignUp.vue'
import UserDeatil from '@/components/myPage/UserDeatil.vue'
import UserModify from '@/components/myPage/UserModify.vue'
import ReviewList from '@/components/review/ReviewList.vue'
import ReviewDetail from '@/components/review/ReviewDetail.vue'
import ReviewUpdate from '@/components/review/ReviewUpdate.vue'
import MyRecordView from '@/views/MyRecordView.vue'
import MyRecordList from '@/components/myPage/MyRecordList.vue'
import MyRecordCreate from '@/components/myRecord/MyRecordCreate.vue'
import MyRecordDetail from '@/components/myRecord/MyRecordDetail.vue'
import MyRecordUpdate from '@/components/myRecord/MyRecordUpdate.vue'
import BoardView from '@/views/BoardView.vue'
import BoardList from '@/components/board/BoardList.vue'
import BoardCreate from '@/components/board/BoardCreate.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'
import BoardUpdate from '@/components/board/BoardUpdate.vue'
import MyPageView from '@/views/MyPageView.vue'
import Profile from '@/components/myPage/Profile.vue'
import MyBoarddList from '@/components/myPage/MyBoarddList.vue'
import MyLikesList from '@/components/myPage/MyLikesList.vue'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/map',
      name: 'map',
      component: MapView,
    },
    {
      path: '/login',
      name: 'login',
      component: UserLogin,
    },
    {
      path: '/signup',
      name: 'signUp',
      component: UserSignUp,
    },
    {
      path: '/calender',
      name: 'calender',
      component: CalenderView,
      meta: { requiresAuth: true },
    },
    {
      path: '/user',
      name: 'user',
      component: HomeView,
      children: [
        {
          path: ":id",
          name: "userDetail",
          component: UserDeatil,
          meta: { requiresOwnership: true },
        },
        {
          path: "modifyUser",
          name: "userModify",
          component: UserModify,
        },
      ],
      meta: { requiresAuth: true },
    },
    {
      path: '/reviews',
      name: 'reviews',
      component: ReviewList,
      meta: { requiresAuth: true },
    },
    {
      path: '/review',
      name: 'reviewHome',
      component: ReviewDetail,
      children: [
        {
          path: ":id",
          name: "review",
          component: ReviewDetail,
        },
      ],
      meta: { requiresAuth: true },
    },
    {
      path: "/review/update",
      name: "reviewUp date",
      component: ReviewUpdate,
      meta: { requiresAuth: true },
    },
    {
      path: '/myrecord',
      name: 'myRecord',
      component: MyRecordView,
      children: [
        {
          path: "",
          name: "myRecordList",
          component: MyRecordList
        },
        {
          path: "create",
          name: "myRecordCreate",
          component: MyRecordCreate
        },
        {
          path: ":id",
          name: "myRecordDetail",
          component: MyRecordDetail,
        },
        {
          path: "update",
          name: "myRecordUpdate",
          component: MyRecordUpdate,
        },
      ],
      meta: { requiresAuth: true },
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView,
      children: [
        {
          path: "",
          name: "boardList",
          component: BoardList
        },
        {
          path: "create",
          name: "boardCreate",
          component: BoardCreate
        },
        {
          path: ":id",
          name: "boardDetail",
          component: BoardDetail
        },
        {
          path: "update",
          name: "boardUpdate",
          component: BoardUpdate,
        },
      ],
      meta: { requiresAuth: true },
    },
    {
      path: '/mypage/:id',
      name: 'myPage',
      component: MyPageView,
      children: [
        {
          path: "",
          name: "profile",
          component: Profile
        },
        {
          path: "userupdate",
          name: "userUpdate",
          component: UserModify
        },
        {
          path: "myrecord",
          name: "myRecordList",
          component: MyRecordList
        },
        {
          path: "myboard",
          name: "myBoardList",
          component: MyBoarddList
        },
        {
          path: "mylikes",
          name: "myLikesList",
          component: MyLikesList
        },
      ],
      meta: { requiresAuth: true, requiresOwnership: true },
    },
  ],
})

// Navigation Guard
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  if (!userStore.loginUser && sessionStorage.getItem('access-token')) {
    await userStore.initializeUser(); // 상태 복원 비동기 대기
  }
  const user = userStore.loginUser;
  
  if (to.meta.requiresAuth && !user) {
    // 인증이 필요한 페이지에 접근하려고 하지만 유저가 로그인하지 않은 경우
    return next({ name: 'login' }); // 로그인 페이지로 리디렉션
  } 
  if (to.meta.requiresOwnership) {
    // 라우트 파라미터의 ID와 로그인한 유저의 ID 비교
    const routeUserId = Number(to.params.id); // 라우트에서 추출한 ID
    const loggedInUserId = user?.id; // 로그인한 유저의 ID

    if (routeUserId !== loggedInUserId) {
      // ID가 일치하지 않으면 접근 불가
      return next({ name: 'home', query: { error: 'access-denied' } });
    }
  }
  next(); // 접근 허용
});

export default router
