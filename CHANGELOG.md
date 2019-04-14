# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.2.0] - 14.04.2019
### Added
- Flyway scripts
- Changelog
- Local mysql datasource

### Changed
- Kotlin version to 1.3.30
- Profile h2 to dev
- Database hosting

### Fixed
- App build

## [1.1.0] - 13.04.2019
### Added
- OAuth exception translator
- Remove unique columns from user model
- Custom OAuth exceptions
- OAuth exception serializer
- Error codes
- User status
- H2 as platform and init data
- Custom banner

### Changed
- Kotlin version to 1.3.11
- User repository now return Set of users
- Extract permission logic to service
- User status evaluator
- User status strategies
- private and public key

### Fixed
- H2 console

## [1.0.0] - 25.03.2019
### Added
- OAuth2 configuration
- Token enhancer
- Entity models
- Custom user detail service
- H2 profile
- Datasource connection
- Private and public key
- Client id and secret
- User, Permission repositories

### Changed
- Kotlin plugin

### Security
- Cors filter
- Security configuration

[Unreleased]: https://github.com/FoioK/Auth-Service/compare/release/1.2.0...HEAD
[1.2.0]: https://github.com/FoioK/Auth-Service/compare/release/1.1.0...release/1.2.0
[1.1.0]: https://github.com/FoioK/Auth-Service/compare/release/1.0.0...release/1.1.0
[1.0.0]: https://github.com/FoioK/Auth-Service/releases/tag/1.0.0