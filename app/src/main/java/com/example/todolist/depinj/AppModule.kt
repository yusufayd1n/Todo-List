package com.example.todolist.depinj

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.TodoDb
import com.example.todolist.data.TodoRepository
import com.example.todolist.data.TodoRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDB(app:Application):TodoDb{
        return Room.databaseBuilder(
            app,
            TodoDb::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db:TodoDb):TodoRepository{
        return TodoRepositoryImplementation(db.dao)
    }
}