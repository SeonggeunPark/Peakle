<template>
    <div>
        <div class="d-flex">
            <!-- Select box -->
            <select class="form-select w-50 me-1" v-model="userLocation">
            <option value="전체" default>전체</option>
            <option value="동구">동구</option>
            <option value="중구">중구</option>
            <option value="서구">서구</option>
            <option value="유성구">유성구</option>
            <option value="대덕구">대덕구</option>
            </select>
            
            <!-- Button -->
            <button class="btn custom-btn w-50" @click="fetchGPTResponse" :disabled="loading">
            {{ loading ? '응답 중...' : 'AI 추천' }}
            </button>
        </div>
        <div v-if="gptResponse" class="response">
        <h3>GPT-4 응답:</h3>
        <p>{{ gptResponse }}</p>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import { useFacilityStore } from '@/stores/facility';

const userMessage = ref(''); // 사용자 입력 메시지
const gptResponse = ref(''); // GPT 응답 메시지
const loading = ref(false); // 로딩 상태
const facilStore = useFacilityStore();
const userLocation = ref('전체');

// GPT-4 Mini API 호출 함수
const fetchGPTResponse = async () => {
//   if (!userMessage.value.trim()) {
//     alert('메시지를 입력해주세요.');
//     return;
//   }

  loading.value = true;

  const extractedArray = facilStore.facilList.map(facil => ({
    name: facil.name,
    address: facil.address,
    crowd: facil.crowd
    }));

  const url = 'https://api.openai.com/v1/chat/completions';
  const apiKey = import.meta.env.VITE_GPT_API_KEY; // OpenAI API 키를 입력하세요

  userMessage.value = `위치와 혼잡도를 입력할건데 (낮을수록 덜 혼잡함) 현재 지역과 가장
  가까우면서 혼잡도가 낮은 암장을 알려줘. 혼잡도 데이터에서 name이 암장 이름, address가 위치
  crowd가 혼잡도야
  우선순위 기준은 지역내 혼잡도가 제일 낮은 암장부터, 그다음은 혼잡도가 평가되지 않은 지역내 암장,
  타지역 혼잡도가 제일 낮은 암장으로 해줘
  *현재 위치 : ${userLocation.value} 지역
  *혼잡도: ${JSON.stringify(extractedArray, null, 2)}
  *답변 형식: 암장 이름만 말해줘`
  const requestData = {
    model: "gpt-4o-mini", // GPT-4 Mini 모델
    messages: [
      { role: 'system', content: 'You are a helpful assistant.' },
      { role: 'user', content: userMessage.value },
    ],
    max_tokens: 100,
    temperature: 0.7,
  };

  try {
    const res = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${apiKey}`,
      },
      body: JSON.stringify(requestData),
    });

    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }

    const data = await res.json();
    gptResponse.value = data.choices[0].message.content;
  } catch (error) {
    console.error('Error fetching GPT response:', error);
    gptResponse.value = 'GPT-4 응답을 가져오는 데 실패했습니다.';
  } finally {
    loading.value = false;
  }
};

</script>

<style scoped>
.custom-btn {
  font-family: 'buttonTitle';
  color: black;
  background-color: #A6E3E9;
}

.custom-btn:hover {
  background-color: #71C9CE;  
}
</style>