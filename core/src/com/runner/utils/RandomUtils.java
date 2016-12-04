package com.runner.utils;

import com.runner.actors.Projectile;
import com.runner.enums.EnemyType;
import com.runner.enums.ProjectileType;

import java.util.Random;

/**
 * Created by bob on 20.11.16.
 */

public class RandomUtils {

    public static EnemyType getRandomEnemyType(){
        RandomEnum<EnemyType> randomEnum = new RandomEnum<EnemyType>(EnemyType.class);
        return randomEnum.random();
    }

    public static ProjectileType getRandomProjectileType(){
        RandomEnum<ProjectileType> randomEnum = new RandomEnum<ProjectileType>(ProjectileType.class);
        return randomEnum.random();
    }

    private static class RandomEnum<E extends Enum> {

        private static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
}
