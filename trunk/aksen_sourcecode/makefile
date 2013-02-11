
# Get Config-File
include config.mak

# C-File to compile
TARGET_C = servocontrol.c
TARGET = $(patsubst %.c,%,$(TARGET_C))

# Perhaps more objects
OBJS = $(TARGET).rel serielle.rel

# --No need to change anything after this line -------------------------------

all:	$(TARGET).ihx

# Link all objects with AkSen.rel
$(TARGET).ihx:	$(OBJS)
	cp "$(AKSEN_REL)" .
	cp $(AKSEN_LST) .
	$(CC) $(L_FLAGS) $(OBJS)
	mv AkSen.ihx $(TARGET).ihx
	- rm *.lst *.sym *.rel *.asm -f
	- rm *.rst *.map *.mem *.lnk -f
	ls -l $(TARGET).ihx

# Compile
%.rel:   %.c
	$(CC) $(C_FLAGS)  $<

clean:
	- rm *.lst *.sym *.rel *.asm -f
	- rm *.rst *.map *.mem *.lnk -f
	- rm *.ihx *.IHX -f