import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/user`

export const useUserStore = defineStore('user', () => {
  const loginUser = ref(null)
  const userInfo = ref({})
  const allUsers = ref([])

  const decodeJwt = (token) => {
    try {
      const tmp = token.split('.');
      const decodedPayload = atob(tmp[1]); // Base64 디코딩
      // UTF-8로 디코딩하기 위해 TextDecoder 사용
      const bytes = new Uint8Array([...decodedPayload].map(char => char.charCodeAt(0)));
      const decodedPayloadUtf8 = new TextDecoder('utf-8').decode(bytes);  // UTF-8로 디코딩

      // 디코딩된 JSON에서 'name'을 추출
      const payload = {
        name: "",
        id: "",
        manager: ""
      };
      payload.name = JSON.parse(decodedPayloadUtf8)['name'];
      payload.id = JSON.parse(atob(tmp[1]))['id'];
      payload.manager = JSON.parse(atob(tmp[1]))['manager'];

      // const payload = JSON.parse(atob(token.split('.')[1])); // JWT Payload 디코딩
      return payload;
    } catch (e) {
      console.error("JWT 디코딩 실패:", e);
      return null;
    }
  };

  // 유저 로그인
  const userLogin = async (userId, password) => {
    try {
      const res = await axios.post(`${REST_API_URL}/login`, { userId, password });

      if (res.status === 202) {
        sessionStorage.setItem('access-token', res.data['access-token']);

        const payload = decodeJwt(res.data['access-token']);
        if (payload) {
          loginUser.value = {
            name: payload.name,
            id: payload.id,
            manager: payload.manager,
          };
          
          router.push({ name: 'home' });
        }
      } else {
        alert("아이디와 비밀번호를 다시 입력해 주십시오");
      }
    } catch (err) {
      console.error("로그인 실패:", err);
    }
  };

  // 유저 로그아웃
  const userLogout = () => {
    sessionStorage.clear();
    loginUser.value = null;
    router.replace({ name: 'login' });
  };

  // 유저 회원가입
  const userSignUp = async (user) => {
    try {
      const res = await axios.post(`${REST_API_URL}/signup`, user);

      if (res.status === 201) {
        alert("회원가입이 완료되었습니다.");
        router.replace({ name: 'login' });
      } else {
        alert("필요한 정보를 모두 입력바랍니다.");
      }
    } catch (err) {
      console.error("회원가입 실패:", err);
    }
  };

  
  // 유저 모든 정보 조회
  const userGet = async () => {
    const token = sessionStorage.getItem('access-token');
    if (!token) return;

    const payload = decodeJwt(token);
    if (!payload) return;

    try {
      const res = await axios.get(`${REST_API_URL}/${payload.id}`);
      if (res.status === 200) {
        userInfo.value = res.data;
        if (userInfo.value.birth) {
          const date = new Date(userInfo.value.birth);
          userInfo.value.birth = date.toISOString().substring(0, 10);
        }
      }
    } catch (err) {
      console.error("유저 정보 조회 실패:", err);
    }
  };

  
  // 전체 유저 조회
  const getAllUsers = async () => {
    try {
      const res = await axios.get(`${REST_API_URL}/users`);
      if (res.status === 200) {
        allUsers.value = res.data;
      } else {
        allUsers.value = null;
      }
    } catch (err) {
      console.error("전체 유저 조회 실패:", err);
    }
  };

  // 유저 수정
  const userUpdate = async () => {
    try {
      const res = await axios.put(REST_API_URL, userInfo.value);
      if (res.status === 200) {
        alert("유저 정보가 수정되었습니다.");
        userInfo.value = null;
        router.replace({ name: 'profile' });
      }
    } catch (err) {
      console.error("유저 정보 수정 실패:", err);
    }
  };

  const initializeUser = async () => {
    return new Promise((resolve) => {
      const token = sessionStorage.getItem('access-token');
      if (token) {
        const payload = decodeJwt(token);
        if (payload) {
          loginUser.value = {
            name: payload.name,
            id: payload.id,
            manager: payload.manager,
          };
        }
      }
      resolve(); // 상태 복원 완료
    });
  };

  return { loginUser,
    userInfo,
    allUsers,
    userLogin,
    userLogout,
    userSignUp,
    userGet,
    userUpdate,
    getAllUsers,
    initializeUser, }
})
