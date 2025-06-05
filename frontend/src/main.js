import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import "./assets/main2.css"
// import "../public/styles/main.css"
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.min.js"
import "bootstrap-icons/font/bootstrap-icons.css"

import { useKakao } from 'vue3-kakao-maps/@utils';
import { useUserStore } from './stores/user'

useKakao(import.meta.env.VITE_KAKAO_API_KEY);

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)


app.use(pinia)
app.use(router)

const userStore = useUserStore();
userStore.initializeUser();

app.mount('#app')