ALTER TABLE "LOG" MODIFY "LIST_IND" NUMBER(10,0);

ALTER TABLE "SYSTEM_INFO" MODIFY "OS_CONFIG" VARCHAR2(200);

create or replace TRIGGER "TRIG_INSTRUCTION_LOG" 
AFTER INSERT OR UPDATE ON INSTRUCTION_LOG
FOR EACH ROW 
BEGIN
  IF INSERTING THEN  
    IF :new.MESSAGE = 'BSSTOBATCH' 
      THEN 
        INSERT INTO I_QUEUE 
        (
              id
            , message
            , param
        )
        VALUES 
        (
            1 -- it is OK to hard code 1 as well. ID is retained for probable future changes
            , :new.MESSAGE
            , 'UserId=' || :new.INSTRUCTING_USER || ',BatchNo=' || :new.BATCH_NO
        );		
     ELSIF :new.MESSAGE = 'BSRUNBATCH' 
      THEN
        INSERT INTO O_QUEUE 
        (
            id
            , message
            , param
        )
        VALUES
        (
            o_queue_seq.nextval
            , 'BSADDINSLG'
            , 'seqNo=' || to_char(:new.SEQ_NO)
        ); 
       END IF;
    ELSIF UPDATING THEN 
      INSERT INTO O_QUEUE 
        (
            id
            , message
            , param
        )
        VALUES
        (
            o_queue_seq.nextval
            , 'BSUPDINSLG'
            , 'seqNo=' || to_char(:new.SEQ_NO)
        );        
   END IF;
END;