<template>
  <div>
    <div class="card border-primary">
      <div class="card-header">
        <h2 class="jh-entity-heading" data-cy="generationProcessDetailsHeading">
          <span v-text="$t('akipGenerationWebApp.generationProcess.home.title')">GenerationProcess</span>
          <div class="d-flex justify-content-end">
            <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
              <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
              <span v-text="$t('akipGenerationWebApp.generationProcess.home.refreshListLabel')">Refresh List</span>
            </button>

            <router-link :to="{ name: 'GenerationProcessStartFormInit' }" custom v-slot="{ navigate }">
              <button
                @click="navigate"
                id="jh-create-akipEntity"
                data-cy="entityCreateButton"
                class="btn btn-warning generation-process-init"
              >
                <font-awesome-icon icon="play"></font-awesome-icon>
                <span v-text="$t('akipGenerationWebApp.generationProcess.home.createLabel')"> Create a new Generation Process </span>
              </button>
            </router-link>
          </div>
        </h2>
      </div>
      <div class="card-body">
        <div class="card">
          <div class="alert alert-warning mt-3 mr-3 ml-3" v-if="!isFetching && generationProcessList && generationProcessList.length === 0">
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
                      <router-link :to="{ name: 'ProcessView', params: { processId: generationProcess.akipProcess.id } }">
                        {{ generationProcess.akipProcess.id }}
                      </router-link>
                    </div>
                  </td>
                  <td>
                    <akip-show-process-instance-status
                      :status="generationProcess.processInstance.status"
                    ></akip-show-process-instance-status>
                  </td>
                  <td>
                    {{
                      generationProcess.processInstance.startDate
                        ? $d(Date.parse(generationProcess.processInstance.startDate), 'short')
                        : ''
                    }}
                  </td>
                  <td>
                    {{
                      generationProcess.processInstance.endDate ? $d(Date.parse(generationProcess.processInstance.endDate), 'short') : ''
                    }}
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
        </div>
      </div>
      <div class="card-footer">
        <div class="container">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./generation-process-list.component.ts"></script>
