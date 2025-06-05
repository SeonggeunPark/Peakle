<template>
    <div class="container">
        <h4>유저 정보 수정</h4>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="userId" placeholder="아이디" v-model="store.userInfo.userId">
            <label for="userId">아이디 <span style="color: red; opacity: 50%;">필수</span></label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="name" placeholder="이름" v-model="store.userInfo.name">
            <label for="name">이름 <span style="color: red; opacity: 50%;">필수</span></label>
        </div>
        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="email" placeholder="이메일" v-model="store.userInfo.email">
            <label for="email">이메일</label>
        </div>
        <div class="form-floating mb-3">
            <input type="Date" class="form-control" id="birth" placeholder="생년월일" v-model="store.userInfo.birth">
            <label for="birth">생년월일</label>
        </div>
        <div class="form-floating mb-3">
            <input type="tel" class="form-control" id="phoneNumber" placeholder="전화번호" v-model="store.userInfo.phoneNumber" @input="validatePhoneNumber" >
            <label for="phoneNumber">전화 번호</label>
        </div>
        <div class="d-flex justify-content-end">
            <button class="btn custom-btn mx-3" @click="updateUser">수정</button>
            <button class="btn custom-btn" @click="backButton">뒤로</button>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { onBeforeMount, onMounted } from 'vue';
import { useRouter } from 'vue-router';


const store = useUserStore();
const router = useRouter();

onBeforeMount(() => {
    store.userGet();
})

const updateUser = function() {
    store.userUpdate()
}
const backButton = function() {
    router.go(-1);
}

</script>

<style scoped>
.custom-btn {
  font-family: 'buttonTitle';
  color: black;
  background-color: #A6E3E9;
}

.custom-btn:hover {
  background-color: #71C9CE;  
}

/* 기록 입력 리스트 */
.custom-record-btn { 
    width: 30px; 
    height: 30px; 
    border-radius: 50%; 
    border: 2px solid #000;
}
</style>