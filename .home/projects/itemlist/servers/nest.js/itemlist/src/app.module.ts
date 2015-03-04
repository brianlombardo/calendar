import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ItemsModule } from './items/items.module';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: 'localhost',
      port: 5432,
      username: 'item_user',
      password: 'item_pass',
      database: 'itemlist',
      autoLoadEntities: true,
      synchronize: true,
    }),
    ItemsModule,
  ],
})
export class AppModule {}
