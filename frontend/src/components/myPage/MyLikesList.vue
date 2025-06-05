<template>
  <div class="container">
    <h4>찜 목록</h4>
    <hr />
    <table class="table table-hover text-center">
      <tbody>
        <!-- 아래의 tr 태그를 반복 -->
        <tr v-for="record in currentPageRecordList" :key="record.facilityId">
          <td style="display: flex; align-items: center;">
            <!-- 이미지 -->
            <img
              :src="`/img/facilLogo/${record.facilityId}.png` || '/img/loading/loading.png'"
              class="facility-icon me-3 rounded"
              style="width: 50px; height: 50px; object-fit: cover;"
            />
            <!-- 텍스트 -->
            <span style="font-size: 16px; font-weight: bold;">{{ record.name }}</span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';
import { useLikeStore } from '@/stores/like';
import { useMyRecordStore } from '@/stores/myRecord';
import { useUserStore } from '@/stores/user';
import { computed, onMounted } from 'vue';

const store = useLikeStore();
const userStore = useUserStore();
const facilStore = useFacilityStore();

onMounted(() => {
  store.getLikes(userStore.loginUser?.id);
})

const currentPageRecordList = computed(() => {
  const tmp_list = store.likeList.map((item) => ({
    facilityId: item.facilityId,
    name: facilStore.facilNames[item.facilityId],
  }));
  return tmp_list;
})

</script>

<style scoped>
td {
  text-align: left;
}
</style>