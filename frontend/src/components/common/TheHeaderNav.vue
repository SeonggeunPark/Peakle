<template>
    <div class="header">
        <nav class="navbar">
            <div class="nav-left">
                <RouterLink to="/" class="logo"><img src="/img/logo/logo_wide_noBack.png"
                        style="height: 5rem; width: auto;">
                </RouterLink>
            </div>
            <div class="nav-center mt-3">
                <RouterLink :to="{ name: 'map' }" @click="facilStore.getFacilList()" class="nav-item px-4">맵</RouterLink>
                <RouterLink :to="{ name: 'boardList' }" class="nav-item px-4">커뮤니티</RouterLink>
                <RouterLink :to="{ name: 'calender' }" class="nav-item px-4">운동 기록</RouterLink>

            </div>
            <div class="nav-right mt-3">
                <RouterLink v-if="store.loginUser" :to="`/mypage/${userId}`" class="nav-item">마이 페이지</RouterLink>
                <RouterLink v-else :to="{ name: 'login' }" class="nav-item">로그인</RouterLink>
            </div>
        </nav>
    </div>

    <!-- <div>
        <nav>
            <RouterLink to="/">Home</RouterLink> | 
            <RouterLink :to="{name: 'map'}" @click="facilStore.getFacilList()">Map</RouterLink> |
            <RouterLink :to="{name: 'boardList'}">board</RouterLink> |
            <RouterLink :to="{ name: 'calender' }">Calender</RouterLink> | 
            <span v-if="store.loginUser">
                <RouterLink :to="`/user/${userId}`">My Page</RouterLink> | 
            </span>
            <span v-else>
                <RouterLink :to="{name: 'login'}">Login</RouterLink> | 
            </span>
        </nav>
    </div> -->
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';
import { useUserStore } from '@/stores/user';
import { onMounted, ref, watch } from 'vue';

const store = useUserStore();
const facilStore = useFacilityStore();
const userId = ref();

onMounted(() => {
    watch(
    () => store.loginUser,
    (newUser) => {
      if (newUser) {
        userId.value = newUser.id;
      }
    },
    { immediate: true } // 초기값도 감지
  );
});

</script>

<style scoped>
.header {
    max-width: 1536px;
    margin: auto;
    margin-left: auto;
    margin-right: auto;
    position: relative;
}

.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.nav-center {
    display: flex;
}

.nav-item {
    font-size: 26px;
    font-weight: 500;
    text-decoration: none;
    color: #000;
    
}

.nav-right .nav-item {
    text-decoration: none;
    color: #000;
}

.nav-item:hover {
    color: #71C9CE;
    /* 강조 색상 변경 */
    transition: color 0.3s;
}
</style>