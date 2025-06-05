<template>
    <div>
        <span v-if="store.loginUser">
            <h2>{{ userName }}님 반갑습니다.</h2>
            <button @click="logout">로그아웃</button>
        </span>
        <!-- <span v-else>
            <input type="text" placeholder="ID입력" v-model.trim="id" />
            <input type="password" placeholder="PW입력" v-model.trim="password" />
            <button @click="login">로그인</button> |
            <RouterLink :to="`/signUp`">회원가입</RouterLink>
        </span> -->

        <!--  html 전체 영역을 지정하는 container -->
        <div class="login-box" id="container" v-else p-0>
            <!--  login 폼 영역을 : loginBox -->
            <div id="loginBox" class="">
                <!-- 로그인 페이지 타이틀 -->
                <div id="loginBoxTitle">
                    <img src="/img/logo/logo_literal.png">
                </div>
                <!-- 아이디, 비번, 버튼 박스 -->
                <div id="inputBox">
                    <div class="input-form-box"><span>아이디 </span><input type="text" name="uid" class="form-control" v-model.trim="id">
                    </div>
                    <div class="input-form-box"><span>비밀번호 </span><input type="password" name="upw"
                            class="form-control" v-model.trim="password"></div>
                    <div class="button-login-box">
                        <button type="button" class="btn btn-primary btn-xs" style="width:100%" @click="login">로그인</button>
                    </div>
                    <div class="button-signup-box">
                        <button type="button" class="btn btn-primary btn-xs" style="width:100%" @click="signup"><RouterLink class="signup" :to="`/signUp`" style="color: white;">회원가입</RouterLink></button>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = useUserStore();
const userName = ref();
const router = useRouter();

onMounted(() => {
    userName.value = store.loginUser?.name;
});

const id = ref('');
const password = ref('');

const login = function () {
    store.userLogin(id.value, password.value);
    id.value = "";
    password.value = "";
    userName.value = "";
    userName.value = store.loginUser?.name;
}
const signup = function() {
    router.push({name:'signUp'})
}

const logout = function () {
    store.userLogout();
}
watch(() => store.loginUser, (newUser) => {
    if (newUser) {
        userName.value = newUser.name;
    } else {
        userName.value = newUser;
    }
})

</script>

<style scoped>
#container {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 100%;
}
img {
    width: 60%;
    height: 100%;
    margin-bottom: 0px;
    position: relative;
}
#loginBox {
    width: 25rem;
    /* height: 25rem; */
    text-align: center;
    background-color: #ffffff;
    border-radius: 10%;
    padding: 10px;
    overflow: hidden;
    margin-bottom: 70px;
}

.input-form-box {
    border: 0px solid #ff0000;
    display: flex;
    margin-bottom: 5px;
}

.input-form-box>span {
    display: block;
    text-align: left;
    padding-top: 5px;
    min-width: 65px;
}

.button-login-box {
    margin: 10px 0;
}
.button-signup-box {
    margin: 10px 0;
}
.signup {
    text-decoration: none;
}

#loginBoxTitle {
    color: #000000;
    font-weight: bold;
    font-size: 32px;
    text-transform: uppercase;
    padding: 5px;
    margin-bottom: 20px;
    background: linear-gradient(to right, #270a09, #8ca6ce);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

#inputBox {
    margin: 10px;
}

#inputBox button {
    padding: 3px 5px;
}
</style>