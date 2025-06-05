<template>
    <div>
        <RouterLink :to="`/myrecord`">기록 목록</RouterLink> | 
        <RouterLink :to="`/reviews`">리뷰 목록</RouterLink>
        <button @click="logout">로그아웃</button>
        <button class="mx-3 btn btn-outline-success" @click="moveUpdate">수정</button>
    </div>
</template>

<script setup>
import router from '@/router';
import { useUserStore } from '@/stores/user';


const store = useUserStore();

const logout = function(){
    store.userLogout();
}

// const moveUpdate = function () {
//     store.
// };
</script>

<style scoped>

</style>