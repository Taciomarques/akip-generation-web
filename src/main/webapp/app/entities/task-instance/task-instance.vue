<template>
  <div class="card border-primary">
    <div class="card-body">
      <task-instance-list />
    </div>
    <div class="card-footer">
      <div class="container">
        <button type="submit" v-on:click.prevent="$router.go(-1)" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>
