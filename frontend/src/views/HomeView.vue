<template>
  <div>
    <!-- 사진 슬라이드 -->
    <div class="image rounded-4 align-self-center mb-4 mt-4">
      <HomeCarousel />
    </div>

    <!-- 탑5 유저 -->
    <div class="top-user mb-5">
      <HomeTop5/>
    </div>

    <!-- 가장 많이 방문한 클라이밍장 -->
     <div class="mb-4">
       <HomeFacility/>
     </div>
    
    <!-- 게시판 최신순 -->
     <div class="mb-4">
       <HomeRecentBoard/>
     </div>
    
    <!-- 게시판 조회수순 -->
     <div class="mb-5">
       <HomePopularBoard/>
     </div>
  </div>
</template>

<script setup>
import HomeCarousel from '@/components/home/HomeCarousel.vue';
import HomeFacility from '@/components/home/HomeFacility.vue';
import HomePopularBoard from '@/components/home/HomePopularBoard.vue';
import HomeRecentBoard from '@/components/home/HomeRecentBoard.vue';
import HomeTop5 from '@/components/home/HomeTop5.vue';
import { useFacilityStore } from '@/stores/facility';
import { onMounted, ref } from 'vue';

onMounted(async () => {
});

</script>

<style scoped>
.image {
  height: 20rem;
  overflow: hidden;
}
</style>