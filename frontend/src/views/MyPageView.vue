<template>
    <div class="mypage-layout">
      <!-- 사이드바 -->
       <div col-3>
         <TheSideBar/>
       </div>
  
      <!-- 콘텐츠 영역 -->
      <main col-9 class="content">
        <RouterView />
      </main>
    </div>
  </template>
  
<script setup>
import TheSideBar from '@/components/common/TheSideBar.vue';

</script>

<style scoped>
.mypage-layout {
display: flex;
}

.content {
flex: 1;
padding: 20px;
}
</style>
  