<template>
  <div>
    <div class="card border-primary">
      <div class="card-header">
        <h2 id="page-heading" data-cy="ProcessHeading">
          <span v-text="$t('akipGenerationWebApp.akipProcess.home.title')" id="process-heading">Processes</span>
          <div class="d-flex justify-content-end">
            <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
              <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
              <span v-text="$t('akipGenerationWebApp.akipProcess.home.refreshListLabel')">Refresh List</span>
            </button>
            <router-link :to="{ name: 'GenerationProcessStartFormInit' }" custom v-slot="{ navigate }">
              <button
                @click="navigate"
                id="jh-create-akipEntity"
                data-cy="entityCreateButton"
                class="btn btn-warning generation-process-init"
              >
                <font-awesome-icon icon="play"></font-awesome-icon>
                <span v-text="$t('akipGenerationWebApp.akipProcess.home.createLabel')"> Create a new Generation Process </span>
              </button>
            </router-link>
          </div>
        </h2>
      </div>
      <div class="card-body">
        <div class="card">
          <div class="alert alert-warning mt-3 mr-3 ml-3" v-if="!isFetching && akipProcesses && akipProcesses.length === 0">
            <span v-text="$t('akipGenerationWebApp.akipProcess.home.notFound')">No processes found</span>
          </div>
          <div class="table-responsive" v-if="akipProcesses && akipProcesses.length > 0">
            <table class="table table-striped" aria-describedby="processes">
              <thead>
                <tr>
                  <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
                  <th scope="row"><span v-text="$t('akipGenerationWebApp.akipProcess.name')">Name</span></th>
                  <th scope="row"><span v-text="$t('akipGenerationWebApp.akipProcess.percentageExecuted')">Percentage Executed</span></th>
                  <th scope="row"><span v-text="$t('akipGenerationWebApp.akipProcess.status')">Status</span></th>
                  <th scope="row"><span v-text="$t('akipGenerationWebApp.akipProcess.application')">Application</span></th>
                  <th scope="row"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="process in akipProcesses" :key="process.id" data-cy="entityTable">
                  <td>
                    <router-link :to="{ name: 'AkipProcessView', params: { processId: process.id } }">{{ process.id }} </router-link>
                  </td>
                  <td>{{ process.name }}</td>
                  <td>
                    <div class="progress" style="border-radius: 5px">
                      <div
                        class="progress-bar progress-bar-striped progress-bar-animated"
                        role="progressbar"
                        :aria-valuenow="process.percentageExecuted"
                        aria-valuemin="0"
                        aria-valuemax="100"
                        :style="'width: ' + process.percentageExecuted + '%;'"
                      >
                        {{ process.percentageExecuted ? process.percentageExecuted + '%' : '0%' }}
                      </div>
                    </div>
                  </td>
                  <td>
                    <show-akip-process-status :value="process.status"></show-akip-process-status>
                  </td>
                  <td>
                    <div v-if="process.application">
                      <router-link :to="{ name: 'AkipApplicationView', params: { akipApplicationId: process.application.id } }"
                        >{{ process.application.name }}
                      </router-link>
                    </div>
                  </td>
                  <td class="text-right">
                    <div class="btn-group">
                      <router-link :to="{ name: 'AkipProcessView', params: { akipProcessId: process.id } }" custom v-slot="{ navigate }">
                        <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                          <font-awesome-icon icon="eye"></font-awesome-icon>
                          <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </button>
                      </router-link>
                      <!--                    <router-link :to="{ name: 'AkipProcessEdit', params: { akipProcessId: process.id } }" custom-->
                      <!--                                 v-slot="{ navigate }">-->
                      <!--                      <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">-->
                      <!--                        <font-awesome-icon icon="pencil-alt"></font-awesome-icon>-->
                      <!--                        <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>-->
                      <!--                      </button>-->
                      <!--                    </router-link>-->
                      <!--                    <b-button-->
                      <!--                      v-on:click="prepareRemove(process)"-->
                      <!--                      variant="danger"-->
                      <!--                      class="btn btn-sm"-->
                      <!--                      data-cy="entityDeleteButton"-->
                      <!--                      v-b-modal.removeEntity-->
                      <!--                    >-->
                      <!--                      <font-awesome-icon icon="times"></font-awesome-icon>-->
                      <!--                      <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>-->
                      <!--                    </b-button>-->
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
      <b-modal ref="removeEntity" id="removeEntity">
        <span slot="modal-title"
          ><span id="akipGenerationWebApp.process.delete.question" data-cy="processDeleteDialogHeading" v-text="$t('entity.delete.title')"
            >Confirm delete operation</span
          ></span
        >
        <div class="modal-body">
          <p id="jhi-delete-process-heading" v-text="$t('akipGenerationWebApp.akipProcess.delete.question', { id: removeId })">
            Are you sure you want to delete this Process?
          </p>
        </div>
        <div slot="modal-footer">
          <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-process"
            data-cy="entityConfirmDeleteButton"
            v-text="$t('entity.action.delete')"
            v-on:click="removeProcess()"
          >
            Delete
          </button>
        </div>
      </b-modal>
    </div>
  </div>
</template>

<script lang="ts" src="./akip-process.component.ts"></script>
