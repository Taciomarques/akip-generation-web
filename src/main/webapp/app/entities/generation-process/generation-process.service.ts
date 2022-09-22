import axios from 'axios';

import { IGenerationProcess } from '@/shared/model/generation-process.model';

const baseApiUrl = 'api/generation-processes';

export default class GenerationProcessService {
  public find(id: number): Promise<IGenerationProcess> {
    return new Promise<IGenerationProcess>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IGenerationProcess): Promise<IGenerationProcess> {
    return new Promise<IGenerationProcess>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
