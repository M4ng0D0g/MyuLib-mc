# GameTeamColor

## Role
This page is the canonical reference for `GameTeamColor` in the `game` docs area.

## Unique responsibility
It represents the team color metadata used by team definitions and facts queries.

## Practical writing
Use this enum when declaring teams or when evaluating whether a team should count as a red-side team.

## Fields
- `DEFAULT`: fallback color.
- `RED`: standard red team color.
- `BLUE`: standard blue team color.
- `GREEN`: standard green team color.
- `YELLOW`: standard yellow team color.
- `AQUA`: aqua team color.
- `WHITE`: white team color.
- `BLACK`: black team color.
- `GRAY`: gray team color.
- `GOLD`: gold team color.
- `LIGHT_PURPLE`: light purple team color.
- `DARK_PURPLE`: dark purple team color.
- `DARK_RED`: dark red team color.
- `DARK_GREEN`: dark green team color.
- `DARK_AQUA`: dark aqua team color.
- `DARK_BLUE`: dark blue team color.
- `DARK_GRAY`: dark gray team color.

## Methods
- `isRed()`: returns `true` for red or dark-red variants.

