<template>
  <div class="app-container" style="margin-left: -35px; ">
    <transition-group tag='ul' name='image' align="center">
       <li v-for='(image,index) in img' :key='image' v-show='index===mark' style="display:none;list-style-type:none;">
          <a><img :src="image"></a>
       </li>
    </transition-group>
  </div>
</template>

<script>
  export default{
    data(){
      return {
        mark:0,
        timer:null,
        img:[
          require('../assets/1.jpg'),
          require('../assets/2.jpg'),
          require('../assets/3.jpg'),
          require('../assets/4.jpg')
        ]
      }
    },
    created(){
      this.play()
    },
    methods: {
      change(i){
        this.mark = i
      },
      autoPlay(){
        this.mark++
        if(this.mark ===4){
          this.mark = 0
          return
        }
      },
      play(){
        setInterval(this.autoPlay, 6000)
      }
    }
  }
</script>

