<template>
  <div>
    <vue-cal
      ref="vuecal"
      selected-date="2024-11-20"
      :time-from="9 * 60"
      :disable-views="['week', 'day']"
      active-view="month"
      hide-weekends
      events-on-month-view="short"
      :events="events"
      @event-click="onEventClick"
      @event-create="onEventCreate"
      style="height: 65rem"
      @cell-dblclick="onCellDblClick"
      :event-render="customEventRender"
    >
    <template #event="{ event }">
      <div class="custom-event">
        <!-- 이미지 표시 -->
        <img :src="event.imageUrl" alt="Event Image" class="event-image" />
        <!-- 이벤트 제목 표시 -->
        <div class="event-title">{{ event.title }}</div>
      </div>
    </template>
  </vue-cal>
  </div>
</template>

<script>

import { useMyRecordStore } from '@/stores/myRecord';
import { ref, onMounted, watchEffect } from 'vue';
import VueCal from 'vue-cal';
import 'vue-cal/dist/vuecal.css';
import { useRouter } from 'vue-router';

export default {
  components: {
    VueCal
  },
  setup() {
    const store = useMyRecordStore();
    const tmp = ref([]);
    const events = ref([]);
    const router = useRouter();

    onMounted(() => {
      store.getMyRecordList(); // 초기 데이터 로드
    });

    // recordList와 events를 동기화
    watchEffect(() => {
      if (store.recordList) {
        events.value = store.recordList.map((item) => ({
          start: new Date(item.created).toISOString().substring(0, 10),
          end: new Date(item.created).toISOString().substring(0, 10),
          title: item.title,
          data: item,
          imageUrl: `/public/img/facilLogo/${item.facilityId}.png`,
        }));
      } else {
        events.value = [];
      }
    });

    const onEventClick = (event) => {
      router.push({name: "myRecordDetail", params: { id: event.data.id}})
    };

    const onEventCreate = (event) => {
      alert(`새로운 이벤트 생성: ${event.title}`);
      const newRecord = {
        created: event.start,
        title: event.title,
        id: Date.now(),
        facilityId: event.data.facilityId || 0
      };

      // store.recordList와 events 동기화
      store.recordList.push(newRecord);
      events.value.push({
        start: newRecord.created,
        end: newRecord.created,
        title: newRecord.title,
        data: newRecord,
        imageUrl: `/public/img/facilLogo/${newRecord.facilityId}.png`
      });
    };

    const onCellDblClick = (event) => {
      store.datetime = new Date(event).toISOString().substring(0, 10)
      router.push({name: "myRecordCreate"})
    };

    const onEventDelete = (event) => {
      const eventIndex = store.recordList.findIndex((item) => item.id === event.data.id);

      if (eventIndex !== -1) {
        store.recordList.splice(eventIndex, 1);
      } if (eventIndex == 0) {
        store.recordList = [];
      }
    };

    return {
      tmp,
      events,
      onEventClick,
      onEventCreate,
      onEventDelete,
      onCellDblClick,
    };
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

/* 지도 색 설정 */
.vuecal {
  height: 90vh;
}

.vuecal__flex.vuecal__menu {
  background-color: #cbf1f5;
  border-bottom: solid rgba(190, 189, 189, 0.495) 1px;
}

.vuecal__title-bar {
  background-color: #cbf1f5;
  border-bottom: solid rgba(190, 189, 189, 0.495) 1px;
}

.vuecal__flex.vuecal__weekdays-headings {
  background-color: #cbf1f5;
  border-bottom: solid rgba(190, 189, 189, 0.495) 1px;
}


/* 이미지 등록 */
.custom-event {
  display: flex;
  align-items: center;
  background-color: #DBE2EF;
  padding: 5px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
  border: 2px solid #A6E3E9;
}

.vuecal--month-view .vuecal__cell-content {
  justify-content: flex-start;
  height: 10rem;
  align-items: flex-center;
}

.event-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 10px;
}

.event-title {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}
</style>
