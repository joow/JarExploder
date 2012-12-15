This folder contains invalid class files made for test purposes, hence their .bin extension.
Their names should be self explanatory but here comes some explanatations :
- InvalidMagicCode : every Java class file should contains 0xCAFE BABE as first eight bytes else the class file is considered as invalid.
- ConstantPoolCountEqualsToZero : after magic code (8 bytes) comes 2 bytes for the minor version, 2 bytes for the major version and 2 bytes for the constant pool count. 
    This count should be equals or greater than 0, the first index of constant pool is 1 (and not 0).
- InvalidConstantPoolInfoTag : then comes the constant pool. Each entry of this pool has a type, identified by a tag. At this moment there are 13 different types (from 1 to 12) so any other type shouldn't exist.