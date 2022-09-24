/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProcessComponent from '../../../../../../main/webapp/app/entities/akip-process/akip-process.vue';
import ProcessClass from '../../../../../../main/webapp/app/entities/akip-process/akip-process.component';
import AkipProcessService from '../../../../../../main/webapp/app/entities/akip-process/akip-process.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Process Management Component', () => {
    let wrapper: Wrapper<ProcessClass>;
    let comp: ProcessClass;
    let processServiceStub: SinonStubbedInstance<AkipProcessService>;

    beforeEach(() => {
      processServiceStub = sinon.createStubInstance<AkipProcessService>(AkipProcessService);
      processServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProcessClass>(ProcessComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          akipProcessService: () => processServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      processServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProcesss();
      await comp.$nextTick();

      // THEN
      expect(processServiceStub.retrieve.called).toBeTruthy();
      expect(comp.processes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      processServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeProcess();
      await comp.$nextTick();

      // THEN
      expect(processServiceStub.delete.called).toBeTruthy();
      expect(processServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
