<template>
  <div class="container-lg">
    <TheHeaderNav/>
    <hr class="mb-5">
    <RouterView />
    <TheFooterNav />
  </div>
</template>

<script setup>
import TheHeaderNav from './components/common/TheHeaderNav.vue';
import { onMounted } from 'vue';
import TheFooterNav from './components/common/TheFooterNav.vue';
import { useFacilityStore } from './stores/facility';

const fasilStore = useFacilityStore();
fasilStore.getFacilNames();

onMounted(() => {
  document.title = '피클 | 쾌적한 클라이밍!';  
})
</script>

<style>
div {
  font-family: "Jua";
  font-optical-sizing: auto;
  font-weight: 400;
  font-style: normal;
}

@font-face {
  font-family:'BMJUA';
 /* font-family:'다른컴포넌트가서 불일 이름'; */
 
  src: url('./fonts/MJUA_ttf.ttf') format('truetype');
 /* src: url('폰트가 저장 되어 있는 공간') format('truetype');*/
 
  font-weight: 400;
}
@font-face {
  font-family:'buttonTitle';
  src: url('@/assets/fonts/buttonTitle.ttf') format('truetype');
  font-weight: 100;
}
</style>