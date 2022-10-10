export interface IAttachment {
  id?: number;
  name?: string | null;
  specificationFile?: string | null;
  specificationFileContentType?: string | null;
  createDateTime?: Date | null;
}

export class Attachment implements IAttachment {
  constructor(
    public id?: number,
    public name?: string | null,
    public specificationFile?: string | null,
    public specificationFileContentType?: string | null,
    public createDateTime?: Date | null
  ) {}
}
