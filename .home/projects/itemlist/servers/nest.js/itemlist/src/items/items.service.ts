import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Like, Repository } from 'typeorm';
import { Item } from './entities/item.entity';

@Injectable()
export class ItemsService {
  constructor(
    @InjectRepository(Item)
    private stringDataRepository: Repository<Item>,
  ) {}

  create(data: string): Promise<Item> {
    const newData = this.stringDataRepository.create({ data });
    return this.stringDataRepository.save(newData);
  }

  findAll(): Promise<Item[]> {
    return this.stringDataRepository.find();
  }

  findOne(id: number): Promise<Item> {
    return this.stringDataRepository.findOne({ where: { id } });
  }

  async update(id: number, data: string): Promise<Item> {
    const existingData = await this.stringDataRepository.findOne({
      where: { id },
    });
    if (!existingData) {
      return null;
    }
    existingData.data = data;
    return this.stringDataRepository.save(existingData);
  }

  async remove(id: number): Promise<void> {
    await this.stringDataRepository.delete(id);
  }

  async search(query: string): Promise<Item[]> {
    return this.stringDataRepository.find({
      where: {
        data: Like(`%${query}%`),
      },
    });
  }
}
