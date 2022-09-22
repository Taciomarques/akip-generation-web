/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import UpdateGenerationProcessStatusAndPercentageExecutedUpdateComponent from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed-update.vue';
import UpdateGenerationProcessStatusAndPercentageExecutedClass from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed-update.component';
import UpdateGenerationProcessStatusAndPercentageExecutedService from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('UpdateGenerationProcessStatusAndPercentageExecuted Management Update Component', () => {
    let wrapper: Wrapper<UpdateGenerationProcessStatusAndPercentageExecutedClass>;
    let comp: UpdateGenerationProcessStatusAndPercentageExecutedClass;
    let updateGenerationProcessStatusAndPercentageExecutedServiceStub: SinonStubbedInstance<UpdateGenerationProcessStatusAndPercentageExecutedService>;

    beforeEach(() => {
      updateGenerationProcessStatusAndPercentageExecutedServiceStub = sinon.createStubInstance<UpdateGenerationProcessStatusAndPercentageExecutedService>(
        UpdateGenerationProcessStatusAndPercentageExecutedService
      );

      wrapper = shallowMount<UpdateGenerationProcessStatusAndPercentageExecutedClass>(
        UpdateGenerationProcessStatusAndPercentageExecutedUpdateComponent,
        {
          store,
          i18n,
          localVue,
          router,
          provide: {
            updateGenerationProcessStatusAndPercentageExecutedService: () => updateGenerationProcessStatusAndPercentageExecutedServiceStub,
          },
        }
      );
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.updateGenerationProcessStatusAndPercentageExecuted = entity;
        updateGenerationProcessStatusAndPercentageExecutedServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(updateGenerationProcessStatusAndPercentageExecutedServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.updateGenerationProcessStatusAndPercentageExecuted = entity;
        updateGenerationProcessStatusAndPercentageExecutedServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(updateGenerationProcessStatusAndPercentageExecutedServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUpdateGenerationProcessStatusAndPercentageExecuted = { id: 123 };
        updateGenerationProcessStatusAndPercentageExecutedServiceStub.find.resolves(
          foundUpdateGenerationProcessStatusAndPercentageExecuted
        );
        updateGenerationProcessStatusAndPercentageExecutedServiceStub.retrieve.resolves([
          foundUpdateGenerationProcessStatusAndPercentageExecuted,
        ]);

        // WHEN
        comp.beforeRouteEnter({ params: { updateGenerationProcessStatusAndPercentageExecutedId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.updateGenerationProcessStatusAndPercentageExecuted).toBe(foundUpdateGenerationProcessStatusAndPercentageExecuted);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
