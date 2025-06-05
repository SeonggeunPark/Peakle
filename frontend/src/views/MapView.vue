<script setup>
import FacilityDetail from '@/components/facility/FacilityDetail.vue';
import { useFacilityStore } from '@/stores/facility';
import { useReviewStore } from '@/stores/review';
import { ref, onMounted, watch, computed, onBeforeMount } from 'vue';
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from 'vue3-kakao-maps';
import TheGptView from '@/components/common/TheGptView.vue'

const facilStore = useFacilityStore();
const reviewStore = useReviewStore();

const lat = ref(36.351001);
const lng = ref(127.384547);
const map = ref();

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const changeLatLng = (facility) => {
  lat.value = facility.lat
  lng.value = facility.lng
};

const isKakaoLoaded = ref(false); // Kakao Maps API 로드 상태

/////////////////////////  watch  /////////////////////////

// 시설 목록 변경 감지
watch(
  () => facilStore.facilList,
  (newVal) => {
    // `facilList`의 크기와 `markerVisible`의 크기를 항상 일치시키도록 배열 초기화
    markerVisible.value = newVal.map(() => false); // 갱신될 때마다 모든 오버레이는 숨김
  },
  // { deep: true, immediate: true } // 시설 목록의 속성 변경까지 감지하고 즉시 실행
);
// 선택한 시설에 따라 시설 정보 갱신
watch(
  () => facilStore.facilDetail,
  (newVal) => {
    selectedFacility.value = newVal;
  },
);
// 선택한 시설에 따라 해당 리뷰를 갱신
const selectedfacilReviews = ref(null)
watch(
  () => reviewStore.facilReviews,
  (newVal) => {
    selectedfacilReviews.value = newVal;
  },
);
// // 검색어 변경 시 필터링
// watch(searchKeyword, (newKeyword) => {
//   filteredGyms.value = gymList.value.filter((gym) =>
//     gym.name.toLowerCase().includes(newKeyword.toLowerCase())
//   );
// });


/////////////////////////// 검색 ////////////////////////////////
const searchKeyword = ref('');
// 필터링된 클라이밍장 리스트 (검색어에 따른 필터링 적용)
const filteredFacilList = ref([]); // 필터링된 클라이밍장 리스트를 저장할 ref

// 시설 목록이 변경될 때 `filteredFacilList` 초기화
watch(() => facilStore.facilList, (newList) => {
  if (newList.length > 0) {
    filteredFacilList.value = newList; // 전체 시설 목록으로 초기화
  }
}, { immediate: true });
// 검색어에 따른 필터링
watch(searchKeyword, (newKeyword) => {
  if (!newKeyword) {
    // 검색어가 없으면 전체 목록 반환
    filteredFacilList.value = facilStore.facilList;
  } else {
    const keyword = newKeyword.toLowerCase();
    filteredFacilList.value = facilStore.facilList.filter(facility =>
      facility.name.toLowerCase().includes(keyword) ||
      facility.address.toLowerCase().includes(keyword)
    );
  }
});
////////////////////////////////////////////////////////////


// 마운트 시 Kakao Maps API 스크립트 로드 및 시설 목록 가져오기
onMounted(async () => {
  loadKakaoScript(); // Kakao Maps API 스크립트 로드 대기
  await facilStore.getFacilList(); // 시설 목록 데이터를 기다림
  filteredFacilList.value = facilStore.facilList;
});

// Kakao Maps API 스크립트 로드 함수
const loadKakaoScript = () => {
  return new Promise((resolve, reject) => {
    if (typeof kakao !== 'undefined' && kakao.maps) {
      isKakaoLoaded.value = true;
      resolve();
    } else {
      const script = document.createElement('script');
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${import.meta.env.VITE_KAKAO_API_KEY}&autoload=false`;
      script.async = true;
      script.onload = () => {
        kakao.maps.load(() => {
          isKakaoLoaded.value = true;
          resolve();
        });
      };
      script.onerror = (err) => reject(err);
      document.head.appendChild(script);
    }
  });
};

const markerVisible = ref([]); // 마커별 오버레이 표시 여부 관리 추가

// const visible = ref(false);
const onClickKakaoMapMarker = (index) => {
  // `markerVisible` 배열에서 해당 인덱스가 존재하는지 확인 후 토글
  if (index >= 0 && index < markerVisible.value.length) {
    if (markerVisible.value[index] == true) {
      markerVisible.value[index] = !markerVisible.value[index];
    } else {
      markerVisible.value = markerVisible.value.map(() => false);
      markerVisible.value[index] = true;
    }
  }
};

// 혼잡도에 따른 마커 이미지 반환 함수
const getMarkerImage = (crowd) => {
  if (crowd < 1.5) {
    return 'img/marker/marker_green.png';
  } else if (crowd >= 1.5 && crowd < 2.5) {
    return 'img/marker/marker_yellow.png';
  } else if (crowd >= 2.5) {
    return 'img/marker/marker_red.png';
  } else {
    return 'img/marker/marker_white.png';
  }
};

const getOverlayContent = (facility) => {
  // 혼잡도에 따른 텍스트와 색상 결정
  const crowdText = facility.crowd < 1.5 ? '여유' 
                  : facility.crowd < 2.5 ? '보통' 
                  : facility.crowd >= 2.5 ? '혼잡' 
                  : '최근 혼잡도 평가 없음';
  
  const crowdColor = facility.crowd < 1.5 ? 'green' 
                    : facility.crowd < 2.5 ? 'orange' 
                    : facility.crowd >= 2.5 ? 'red' 
                    : 'gray';

  return `
    <div
      style="
        padding: 10px;
        background-color: white;
        border-color: black;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        margin-bottom: 5px;
      "
    >
      <div style="font-weight: bold; margin-bottom: 5px;">${facility.name}</div>
      <div style="display: flex">
        <div style="margin-right: 10px; border-radius: 50%;">
          <img src="${`/img/facilLogo/${facility.id}.png` || 'img/loading/loading.png'}" width="73" height="70" style="border-radius: 10%;" />
        </div>
        <div style="display: flex; flex-direction: column; align-items: flex-start;">
          <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${facility.name}</div>
          <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">현재 혼잡도:
            <span style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; color: ${crowdColor}; font-weight: 500;">
              ${crowdText}
            </span>
            
            </div>
        </div>
      </div>
    </div>`;
};

const isVisibleSideBar = ref(false); // 사이드바 표시 여부 관리

// 사이드바의 표시 상태를 변경하는 함수
const toggleSidebar = () => {
  isVisibleSideBar.value = !isVisibleSideBar.value;
};

///////////////////// 사이드바 관련 /////////////////////////

const isVisibleDetailSidebar = ref(false); // 상세 정보 사이드바 표시 여부 관리
const selectedFacility = ref(null); // 선택된 클라이밍장 데이터

// 마커 클릭 또는 리스트 클릭 시 상세 정보 사이드바 표시 함수
const showDetailSidebar = (facility) => {
  facilStore.getFacilDetail(facility.id)
  selectedFacility.value = facilStore.facilDetail;
  selectedfacilReviews.value = reviewStore.facilReviews;
  isVisibleDetailSidebar.value = true;
};

// 상세 정보 사이드바 닫기 함수
const toggleDetailSidebar = () => {
  isVisibleDetailSidebar.value = !isVisibleDetailSidebar.value;
};
</script>

<template>
  <div class="container text-center">
    <div class="d-flex justify-content-center" style="height: 50rem;">
      <!-- 왼쪽 사이드바: 검색 및 클라이밍장 리스트 표시 -->
      <div class="left-sidebar bg-light pe-1" style="width: 25%; height: 100%;">
        <!-- 검색 영역 -->
         <TheGptView/>
        <div class="search-area mb-3 card p-2 shadow-sm">
          <div class="input-group">
            <input v-model="searchKeyword" type="text" placeholder="암장을 검색하기" class="form-control border-0" />
            <span class="input-group-text bg-transparent border-0" style="cursor: pointer;">
              <i class="bi bi-search"></i>
            </span>
          </div>
        </div>

        <!-- 클라이밍장 리스트 -->
        <div class="list-area">
          <ul class="list-group">
            <li v-for="(facility, index) in filteredFacilList" :key="facility.id"
              class="list-group-item d-flex align-items-center p-3 mb-3 shadow-sm"
              @click="showDetailSidebar(facility); changeLatLng(facility); onClickKakaoMapMarker(index); reviewStore.getReviews(facility.id)"
              style="cursor: pointer;">
              <img :src="`/img/facilLogo/${facility.id}.png` || 'img/loading/loading.png'" class="facility-icon me-3 rounded"
                style="width: 50px; height: 50px; object-fit: cover;" />
              <div>
                <h6 class="mb-0">{{ facility.name }}</h6>
                <p class="mb-0 text-muted small">{{ facility.address }}</p>
              </div>
            </li>
          </ul>
        </div>
      </div>

      <!-- 상세 사이드바  -->
      <div class="d-flex justify-content-center p-1" style="width: 100%; overflow: hidden">
        <div class="detail-sidebar bg-light position-static top-0 start-0"
          :class="{ 'detail-sidebar-visible': isVisibleDetailSidebar }" v-if="isVisibleDetailSidebar && selectedFacility && selectedfacilReviews"
          style="width: 25rem;">
          <!-- <div v-if="selectedFacility && selectedfacilReviews"> -->
            <div class="text-start">
              <button type="button" class="btn-close" aria-label="Close" @click="toggleDetailSidebar(item)"></button>
            </div>
            <FacilityDetail class="detail-sidebar bg-light position-relative"
              :class="{ 'detail-sidebar-visible': isVisibleDetailSidebar }" v-if="isVisibleDetailSidebar"
              style="width: 100%; height: 100%;" :selectedFacility="selectedFacility"
              :selectedfacilReviews="selectedfacilReviews"></FacilityDetail>
          <!-- </div> -->
        </div>

        <!-- 지도 -->
        <div id="map" v-if="isKakaoLoaded" @onLoadKakaoMap="onLoadKakaoMap"
          style="width: 100%; background-color: aqua;">
          <KakaoMap :lat="lat" :lng="lng" :level="6" style="height: 100%; width: 100%;">
            <KakaoMapMarker v-for="(item, index) in facilStore.facilList" :key="item.id" :lat="item.lat" :lng="item.lng"
              :image="{
                imageSrc: getMarkerImage(item.crowd),
                imageWidth: 55,
                imageHeight: 55,
                imageOption: {},
              }" :opacity="0.99" :clickable="true"
              @onClickKakaoMapMarker="onClickKakaoMapMarker(index); showDetailSidebar(item); changeLatLng(item); reviewStore.getReviews(item.id)" />
            <KakaoMapCustomOverlay v-for="(item, index) in facilStore.facilList" :key="item.id" :lat="item.lat"
              :lng="item.lng" :yAnchor="1.4" :visible="markerVisible[index]" :content="getOverlayContent(item)" />
          </KakaoMap>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

 
::-webkit-scrollbar {
  display: none;
}
.left-sidebar {
  z-index: 1010;
  height: 100%;
  width: 25%;
  left: 0px;
  top: 0px;
  bottom: 0px;
  background: #FFFFFF;
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1), 0px 1px 2px -1px rgba(0, 0, 0, 0.1)
}

.detail-sidebar {
  z-index: 1015;
  overflow-y: auto;
  scrollbar-width: 0;
  margin-right: 8px;
}

.kakaomap {
  position: absolute;
  z-index: 1000;
  width: 100%;
  height: 100%;
}

.left-sidebar {
  z-index: 1010;
  height: 100%;
  width: 25%;
  background: #FFFFFF;
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1), 0px 1px 2px -1px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
}

.search-area {
  background-color: #f8f9fa;
  border-radius: 0.5rem;
}

.list-group-item {
  border: none;
  /* 리스트 간 경계선을 없앰 */
  background-color: #fff;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.list-group-item:hover {
  transform: translateY(-2px);
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.facility-icon {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 8px;
}
</style>