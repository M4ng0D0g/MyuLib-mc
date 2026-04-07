# GameTeamDefinition

## Role
This page is the canonical reference for `GameTeamDefinition` in the `game` docs area.

## Unique responsibility
It stores team metadata that can be created from code and then bound to a game instance.

## Practical writing
Use this record when declaring teams in a game definition or bootstrap-like setup.

## Fields
- `id`: unique team id.
- `displayName`: human-readable team name.
- `color`: team color metadata.
- `friendlyFire`: whether teammates can damage each other.
- `seeFriendlyInvisibles`: whether teammates can see each other when invisible.
- `properties`: immutable custom property map.

## Methods
- Canonical record accessors are generated automatically by Java.
- Constructors: Java canonical and convenience constructors normalize `color` and `properties`.

