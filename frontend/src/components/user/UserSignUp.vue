<template>
    <div class="container">
        <h4>유저 회원가입</h4>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="userId" placeholder="아이디" v-model="user.userId">
            <label for="userId">아이디</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="password" placeholder="비밀번호" v-model="user.password">
            <label for="password">비밀번호</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="passwordCheck" placeholder="비밀번호 확인" v-model="passwordCheck">
            <label for="passwordCheck">비밀번호 확인</label>
        </div>
        <p v-if="passwordError" class="text-danger">비밀번호와 비밀번호 확인이 다릅니다.</p>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="name" placeholder="이름" v-model="user.name">
            <label for="name">이름</label>
        </div>
        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="email" placeholder="이메일" v-model="user.email">
            <label for="email">이메일</label>
        </div>
        <div class="form-floating mb-3">
            <input type="Date" class="form-control" id="birth" placeholder="생년월일" v-model="user.birth">
            <label for="birth">생년월일</label>
        </div>
        <div class="form-floating mb-3">
            <input type="tel" class="form-control" id="phoneNumber" placeholder="전화번호" v-model="user.phoneNumber" @input="validatePhoneNumber" >
            <label for="phoneNumber">전화 번호</label>
        </div>
        <div class="d-flex justify-content-end">
            <button class="btn btn-outline-primary" @click="createUser">등록</button>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref } from 'vue';


const user = ref({
    userId: "",
    password: "",
    name: "",
    email: "",
    birth: "",
    phoneNumber: "",
})

const passwordCheck = ref('');
const passwordError = ref(false);

const store = useUserStore();

const createUser = function() {
    if (user.value.password !== passwordCheck.value) {
        passwordError.value = true;
    } else if (user.value.userId=="" || user.value.password=="" || user.value.name=="" ||
    user.value.email=="" || user.value.birth=="" || user.value.phoneNumber==""
    ) {
        alert("입력 사항을 확인해 주십시오.")
    }
    else {
        passwordError.value = false;
        store.userSignUp(user.value);
    }
}

const validatePhoneNumber = function() {
    user.value.phoneNumber = user.value.phoneNumber.replace(/[^0-9]/g, '');
}

</script>

<style lang="scss" scoped>

</style>