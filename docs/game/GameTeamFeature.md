# GameTeamFeature

## Role
This page is the canonical reference for `GameTeamFeature` in the `game` docs area.

## Unique responsibility
It stores registered team definitions, runtime team data, and player membership mappings for a game instance.

## Practical writing
Use this feature from `GameInstance.teams()` when you want to register teams, add players, or query team membership.

## Fields
- `definitions`: registered `GameTeamDefinition` entries keyed by team id.
- `runtimeTeams`: optional runtime backing objects for team-specific implementation details.
- `teamMembers`: members keyed by team id.
- `playerTeams`: reverse lookup from player id to team id.

## Methods
- `register(GameTeamDefinition definition)`: registers a team definition and creates its member set.
- `bindRuntime(Identifier teamId, Object runtimeTeam)`: binds a runtime object to a registered team.
- `getRuntime(Identifier teamId)`: looks up the runtime object for a team.
- `getDefinition(Identifier teamId)`: looks up the team definition.
- `addPlayer(Identifier teamId, Identifier playerId)`: adds a player to the team.
- `removePlayer(Identifier playerId)`: removes a player from whichever team they currently belong to.
- `isOnTeam(Identifier playerId, Identifier teamId)`: checks membership.
- `playerCount(Identifier teamId)`: counts members in a specific team.
- `playerCount()`: counts all tracked team memberships.
- `teamOf(Identifier playerId)`: returns the team id for a player.
- `clear()`: clears all team data.
