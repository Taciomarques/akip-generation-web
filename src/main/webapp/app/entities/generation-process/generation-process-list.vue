<template>
  <div>
    <h2 class="jh-entity-heading" data-cy="generationProcessDetailsHeading">
      <span v-text="$t('akipGenerationWebApp.generationProcess.home.title')">GenerationProcess</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('akipGenerationWebApp.generationProcess.home.refreshListLabel')">Refresh List</span>
        </button>

        <router-link :to="`/process-definition/GenerationProcess/init`" tag="button" class="btn btn-primary mr-2">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('akipGenerationWebApp.generationProcess.home.createLabel')"> Create a new Travel Plan Process Instance </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && generationProcessList && generationProcessList.length === 0">
      <span v-text="$t('akipGenerationWebApp.generationProcess.home.notFound')">No generationProcess found</span>
    </div>
    <div class="table-responsive" v-if="generationProcessList && generationProcessList.length > 0">
      <table class="table table-striped" aria-describedby="generationProcessList">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span>Process Instance</span></th>
            <th scope="row"><span>Process</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"><span>Start Date</span></th>
            <th scope="row"><span>End Date</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="generationProcess in generationProcessList" :key="generationProcess.id" data-cy="entityTable">
            <td>{{ generationProcess.id }}</td>
            <td>
              <div v-if="generationProcess.processInstance">
                <router-link :to="`/process-definition/GenerationProcess/instance/${generationProcess.processInstance.id}/view`">
                  {{ generationProcess.processInstance.businessKey }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="generationProcess.akipProcess">
                <router-link :to="{ name: 'ProcessView', params: { processId: generationProcess.akipProcess.id } }">{{
                  generationProcess.akipProcess.id
                }}</router-link>
              </div>
            </td>
            <td>
              <akip-show-process-instance-status :status="generationProcess.processInstance.status"></akip-show-process-instance-status>
            </td>
            <td>
              {{ generationProcess.processInstance.startDate ? $d(Date.parse(generationProcess.processInstance.startDate), 'short') : '' }}
            </td>
            <td>
              {{ generationProcess.processInstance.endDate ? $d(Date.parse(generationProcess.processInstance.endDate), 'short') : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="`/process-definition/GenerationProcess/instance/${generationProcess.processInstance.id}/view`"
                  tag="button"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
      <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
    </button>
  </div>
</template>

<script lang="ts" src="./generation-process-list.component.ts"></script>
