<template>
  <div id="app">
    <ribbon></ribbon>
    <div id="app-header">
      <jhi-navbar></jhi-navbar>
    </div>
    <div>
      <sidebar-menu :menu="calculateMenuItems" @toggle-collapse="onToggleCollapse" width="300px"> </sidebar-menu>
    </div>
    <div id="content" :class="[{ collapsed: collapsed }, { onmobile: isOnMobile }]">
      <div class="container-fluid">
        <div class="card jh-card">
          <router-view></router-view>
        </div>
        <b-modal id="login-page" hide-footer lazy>
          <span data-cy="loginTitle" slot="modal-title" id="login-title" v-text="$t('login.title')">Sign in</span>
          <login-form></login-form>
        </b-modal>

        <jhi-footer></jhi-footer>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>

<style scoped>
#app {
  background-color: #f0f0f0;
  min-height: 100vh;
}

#content {
  padding-left: 300px;
  transition: 0.3s ease;
  overflow: scroll;
  margin-top: 70px;
}

#main-card {
  background-color: #fff;
  border-radius: 0.35em;
  //min-height: 85vh;
  border: 1px solid #cecece;
}

#content.collapsed {
  padding-left: 50px;
}

#content.onmobile {
  padding-left: 50px;
}

.v-sidebar-menu {
  margin-top: 70px;
  scroll-margin-top: 5px;
}

#app-header {
  position: fixed;
  z-index: 10;
  width: 100%;
  top: 0;
}
</style>
