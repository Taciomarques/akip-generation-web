<template>
  <div>
    <h2 id="page-heading" data-cy="EntidadeHeading">
      <span v-text="$t('akipGenerationWebApp.entidade.home.title')" id="entidade-heading">Entidades</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('akipGenerationWebApp.entidade.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EntidadeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-entidade"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('akipGenerationWebApp.entidade.home.createLabel')"> Create a new Entidade </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && entidades && entidades.length === 0">
      <span v-text="$t('akipGenerationWebApp.entidade.home.notFound')">No entidades found</span>
    </div>
    <div class="table-responsive" v-if="entidades && entidades.length > 0">
      <table class="table table-striped" aria-describedby="entidades">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.entidade.name')">Name</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.entidade.fields')">Fields</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.entidade.relations')">Relations</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.entidade.type')">Type</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.entidade.process')">Process</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.entidade.application')">Application</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entidade in entidades" :key="entidade.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EntidadeView', params: { entidadeId: entidade.id } }">{{ entidade.id }}</router-link>
            </td>
            <td>{{ entidade.name }}</td>
            <td>{{ entidade.fields }}</td>
            <td>{{ entidade.relations }}</td>
            <td v-text="$t('akipGenerationWebApp.TypeEntity.' + entidade.type)">{{ entidade.type }}</td>
            <td>
              <div v-if="entidade.process">
                <router-link :to="{ name: 'ProcessView', params: { processId: entidade.process.id } }">{{
                  entidade.process.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="entidade.application">
                <router-link :to="{ name: 'ApplicationView', params: { applicationId: entidade.application.id } }">{{
                  entidade.application.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EntidadeView', params: { entidadeId: entidade.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EntidadeEdit', params: { entidadeId: entidade.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(entidade)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="akipGenerationWebApp.entidade.delete.question" data-cy="entidadeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-entidade-heading" v-text="$t('akipGenerationWebApp.entidade.delete.question', { id: removeId })">
          Are you sure you want to delete this Entidade?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-entidade"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEntidade()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./entidade.component.ts"></script>
