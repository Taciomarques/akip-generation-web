<template>
  <div class="card border-primary">
    <div class="card-header">
      <h2>
        <span id="health-page-heading" v-text="$t('health.title')" data-cy="healthPageHeading">Health Checks</span>
        <button class="btn btn-primary float-right" v-on:click="refresh()" :disabled="updatingHealth">
          <font-awesome-icon icon="sync"></font-awesome-icon>
          <span v-text="$t('health[\'refresh.button\']')">Refresh</span>
        </button>
      </h2>
    </div>
    <div class="card-body">
      <div class="table-responsive">
        <table id="healthCheck" class="table table-striped" aria-describedby="Health check">
          <thead>
            <tr>
              <th v-text="$t('health.table.service')" scope="col">Service Name</th>
              <th class="text-center" v-text="$t('health.table.status')" scope="col">Status</th>
              <th class="text-center" v-text="$t('health.details.details')" scope="col">Details</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="health of healthData" :key="health.name">
              <td><span v-text="$t('health.indicator.' + baseName(health.name))" /> {{ subSystemName(health.name) }}</td>
              <td class="text-center">
                <span class="badge" :class="getBadgeClass(health.status)" v-text="$t('health.status.' + health.status)">
                  {{ health.status }}
                </span>
              </td>
              <td class="text-center">
                <a class="hand" v-on:click="showHealth(health)" v-if="health.details || health.error">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                </a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="card-footer">
      <div class="container">
        <button type="submit" v-on:click.prevent="$router.go(-1)" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
    <b-modal ref="healthModal">
      <h4 slot="modal-title" v-if="currentHealth" class="modal-title" id="showHealthLabel">
        <span v-text="$t('health.indicator.' + baseName(currentHealth.name))" />
        {{ subSystemName(currentHealth.name) }}
      </h4>
      <health-modal :current-health="currentHealth"></health-modal>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./health.component.ts"></script>
