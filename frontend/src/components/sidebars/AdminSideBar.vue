<template>
    <div class="adminSideBar">
        <ul class="topSideBar">
            <a href="#">Manage Users</a>
            <div class="border">
                <v-divider></v-divider>
            </div>
            <a href="#">User Info</a>
            <a href="#" style="padding-top: 20px">Manage Inqury</a>
            <div class="border">
                <v-divider></v-divider>
            </div>
            <a href="#">Inqury List</a>
            <a href="#" style="padding-top: 20px">Manage Report</a>
            <div class="border">
                <v-divider></v-divider>
            </div>
            <a href="#">Reported Comment List</a>
            <a href="#">Reported Article List</a>
        </ul>
        <ul class="bottomSideBar">
            <a href="#">Account</a>
             <div class="border">
                <v-divider></v-divider>
            </div>
            <a href="#">Create Reporter ID</a>
            <a href="#">Reporter List</a>
            <a href="#">Waiting Articles</a>
        </ul>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>

.adminSideBar{
    margin-top: 50px;
    width: 420px;
    height: 100%;
}
.topSideBar{
    height: 350px;
    width: 190px;
    background-color: #f7f7f5;
    padding: 16px 0 16px 15px
}

.topSideBar > a:nth-child(1),
.topSideBar > a:nth-child(4),
.topSideBar > a:nth-child(7),
.bottomSideBar  > a:nth-child(1)
{
    font-size: 14px;
    font-weight: bold;
    padding: 0;
}

.adminSideBar a{
    display: flex;
    color: #4a4a4a;
    text-decoration: none;
    padding: 5px 0 0 0;
    font-weight: 400;
    font-size: 13px;
    margin-bottom: 5px;
}

.bottomSideBar{
    margin-top: 20px;
    height: 250px;
    width: 190px;
    background-color: #f7f7f5;
    padding: 16px 0 16px 15px
}

.border{
    width: 85%;
    border: 1px light black;
}
</style>