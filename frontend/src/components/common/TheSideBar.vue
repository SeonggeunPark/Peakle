<template>
    <aside class="sidebar">
        <ul class="menu">
            <li>
                <RouterLink :to="{ name: 'profile' }" class="menu-item">프로필</RouterLink>
            </li>
            <li>
                <RouterLink :to="{ name: 'userUpdate' }" class="menu-item">회원정보</RouterLink>
            </li>
            <li>
                <RouterLink :to="{ name: 'myLikesList' }" class="menu-item">찜 목록</RouterLink>
            </li>
            <li>
                <RouterLink :to="{ name: 'myRecordList' }" class="menu-item">내 운동 기록</RouterLink>
            </li>
            <li>
                <RouterLink :to="{ name: 'myBoardList' }" class="menu-item">내 게시글</RouterLink>
            </li>
            <li>
                <button @click="logout" class="menu-item btn-logout">로그아웃</button>
            </li>
        </ul>
    </aside>
</template>
<script setup>
import { useUserStore } from '@/stores/user';

const store = useUserStore();

const logout = function () {
    store.userLogout();
}
</script>

<style scoped>
.sidebar {
    width: 250px;
    background-color: #f8f9fa;
    border-right: 1px solid #e9ecef;
    height: 100vh;
    padding: 20px;
}

.menu {
    list-style: none;
    padding: 0;
}

.menu-item {
    padding: 10px 15px;
    display: block;
    color: #333;
    text-decoration: none;
    border-radius: 5px;
    margin-bottom: 10px;
    transition: background-color 0.3s;
}

.menu-item:hover {
    background-color: #e9ecef;
}

.btn-logout {
  background: none;
  border: none;
  color: #333;
  text-align: center;
  width: 100%;
  cursor: pointer;
}
</style>