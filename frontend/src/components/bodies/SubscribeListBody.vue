<template>
    <div class="subListComponent">
        <h1 class="title">Subscription List</h1>
        <div class="tableContainer">
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                        <tr>
                            <th class="text-left" style="font-size:15px">
                                Reporter
                            </th>
                            <th class="text-left" style="font-size:15px">
                                NewsLetter
                            </th>
                            <th class="text-left" style="font-size:15px">
                                Subscribe
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                        v-for="item in desserts"
                        :key="item.name"
                        >
                            <td>이푸름</td>
                            <td> 
                              <v-switch
                              v-model="switch1"
                              :label="`Switch 1: ${switch1.toString()}`"
                              ></v-switch>
                            </td>
                            <td>
                                <v-btn
                                small
                                color="primary"
                                dark
                                >
                                Cancle
                              </v-btn>
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <div class="text-center paging">
          <v-pagination
            v-model="page"
            :length="6"
          ></v-pagination>
        </div>
    </div>
</template>

<script>
export default {
    data () {
      return {
        switch1: true,
        desserts: [
          {
            name: 'Frozen Yogurt',
            calories: 159,
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
          },
        ],
        page: 1,
      }
    },
}
</script>

<style scoped>
.tableContainer{
    margin-top: 50px;
}

.paging{
  margin: 60px 175px 0 0;
}

</style>