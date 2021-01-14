<template>
   <div class="sidebar">
        <ul class="sidebar__menus">
            <li v-for="(menu, i) in sideMenu" :key="i">
                <template v-if="menu.type === 'title'">
                    <div :class="title">
                        {{ menu.text }}
                    </div>
                </template>
                <template v-if="menu.type === 'sub'">
                    <router-link :to="menu.link"
                    :class="'hoverAction' + sub">
                        {{ menu.text }}
                    </router-link>
                </template>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    props: ['sideMenu'],
    data: () => ({
        title: ' subtitle-1 font-weight-bold',
        sub: ' caption font-weight-medium'
    }),
}
</script>

<style scoped>
li {
    list-style: none;
}

.sidebar{
    /* width: 320px; */
    width: 100%;
    /* background-color: #f7f7f5; */
    /* position: fixed; */
    height: 100%;
}
.sidebar__menus{
    /* height: 300px; */
    width: 190px;
    background-color: #f7f7f5;
    padding: 40px 0 40px 32px;
}
.sidebar__menus > a:nth-child(1){
    font-size: 17px;
    font-weight: bold;
}
.sidebar a{
    display: flex;
    color: #4a4a4a;
    text-decoration: none;
    padding: 10px;
    font-weight: 600;
    font-size: 13px;
    margin-bottom: 5px;
}

.title {
    color: black;
    font-size: 8px;
}

.sub {
    color: #4a4a4a;
    text-decoration: none;
    font-size: 13px;
}

.hoverAction:hover {
  text-decoration: underline;
}
</style>